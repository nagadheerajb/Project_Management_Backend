package fs19.java.backend.config;

import fs19.java.backend.application.UserDetailsServiceImpl;
import fs19.java.backend.presentation.shared.exception.AuthenticationNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.UUID;

/**
 * JWT request filter
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LogManager.getLogger(JwtAuthFilter.class);
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtValidator jwtValidator;

    public JwtAuthFilter(UserDetailsServiceImpl userDetailsService, JwtValidator jwtValidator) {
        this.userDetailsService = userDetailsService;
        this.jwtValidator = jwtValidator;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // 0) Possibly skip swagger or other public endpoints
            String requestURI = request.getRequestURI();
            String httpMethod = request.getMethod();
            //logger.info("Incoming request: [{}] {}", httpMethod, requestURI);

            // Log all request headers for debugging
//            request.getHeaderNames().asIterator().forEachRemaining(headerName ->
//                    logger.info("Request Header: {} = {}", headerName, request.getHeader(headerName))
//            );

            if (isPublicEndpoint(requestURI)) {
                filterChain.doFilter(request, response);
                return;
            }

            // 1) Get token + workspaceId from headers
            String token = request.getHeader("Authorization");
            String workspaceId = request.getHeader("workspaceId");

            if (token == null) {
                logger.warn("No token found in request headers");
                filterChain.doFilter(request, response);
                return;
            }

            // 2) Extract username from token
            String username = null;
            String signature = getSignature(token);
            if (signature != null) {
                username = jwtValidator.extractUserEmail(token);
                logger.info("Extracted username from token: {}", username);
            }

            if (username == null) {
                logger.warn("Unable to extract username from token");
                filterChain.doFilter(request, response);
                return;
            }

            // 3) If already authenticated => skip
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                logger.info("User already authenticated");
                filterChain.doFilter(request, response);
                return;
            }

            // 4) Distinguish between /my-workspaces GET and all other endpoints
            boolean isMyWorkspacesEndpoint = ("/api/v1/workspace-users/my-workspaces".equals(requestURI)
                    && "GET".equalsIgnoreCase(httpMethod));

            if (isMyWorkspacesEndpoint) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtValidator.isTokenValidForNoWorkspace(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    userDetailsService.findUserByUserName(username),
                                    userDetails.getAuthorities()
                            );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    logger.info("Authenticated user for endpoint /my-workspaces: {}", username);
                } else {
                    logger.warn("Token is invalid for endpoint /my-workspaces: {}", username);
                }
            } else {
                if (workspaceId != null) {
                    UserDetails userDetails = userDetailsService.loadUserByUserNameAndWorkspaceId(
                            username, UUID.fromString(workspaceId));
                    if (jwtValidator.isTokenValid(token, userDetails, workspaceId)) {
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        userDetailsService.findUserByUserName(username),
                                        userDetails.getAuthorities()
                                );
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        logger.info("Authenticated user for endpoint: {}", requestURI);
                    } else {
                        logger.warn("Token is invalid for endpoint: {}", requestURI);
                    }
                } else {
                    logger.warn("No workspaceId found for endpoint: {}", requestURI);
                }
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            logger.error("Exception during authentication process: ", e);
            throw new RuntimeException(e);
        }
    }


    private void handleUserInfo(HttpServletRequest request, String token, UserDetails userDetails, String workspaceId, String username) {
        if (jwtValidator.isTokenValid(token, userDetails, workspaceId)) {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, userDetailsService.findUserByUserName(username), userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }

    /**
     * Get the signature
     *
     * @param jwt
     * @return
     */
    public String getSignature(String jwt) {
        String[] jwtParts = jwt.split("\\.");
        return jwtParts[1];
    }

    private boolean isPublicEndpoint(String requestURI) {
        return requestURI.startsWith("/swagger-ui/") ||
                requestURI.startsWith("/v3/api-docs") ||
                requestURI.startsWith("/swagger-resources") ||
                requestURI.startsWith("/webjars/") ||
                requestURI.startsWith("/api/v1/invitation/");
    }

}
