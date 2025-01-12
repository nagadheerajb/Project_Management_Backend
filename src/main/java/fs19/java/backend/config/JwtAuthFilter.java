package fs19.java.backend.config;

import fs19.java.backend.application.UserDetailsServiceImpl;
import fs19.java.backend.presentation.shared.exception.AuthenticationNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
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
            if (isPublicEndpoint(requestURI)) {
                filterChain.doFilter(request, response);
                return;
            }

            // 1) Get token + workspaceId from headers
            String token = request.getHeader("Authorization");
            String workspaceId = request.getHeader("workspaceId");

            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }

            // 2) Extract username from token
            String username = null;
            String signature = getSignature(token);
            if (signature != null) {
                username = jwtValidator.extractUserEmail(token);
            }
            if (username == null) {
                filterChain.doFilter(request, response);
                return;
            }

            // 3) If already authenticated => skip
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
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
                    }
                }
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {
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
