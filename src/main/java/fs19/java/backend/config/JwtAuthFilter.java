package fs19.java.backend.config;

import fs19.java.backend.application.UserDetailsServiceImpl;
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
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            //allow swagger
            String requestURI = request.getRequestURI();
            if (requestURI.startsWith("/swagger-ui/") ||
                    requestURI.startsWith("/v3/api-docs") ||
                    requestURI.startsWith("/swagger-resources") ||
                    requestURI.startsWith("/webjars/")) {
                filterChain.doFilter(request, response);
                return;
            }
            String token = request.getHeader("Authorization");
            // toDO: consider workspace_id : later
            String username = null;
            if (token != null) {
                String signature = getSignature(token);
                if (signature != null) {
                    username = jwtValidator.extractUserEmail(token);
                }
            }
            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtValidator.isTokenValid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (AccessDeniedException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the signature
     * @param jwt
     * @return
     */
    public String getSignature(String jwt) {
        String[] jwtParts = jwt.split("\\.");
        return jwtParts[1];
    }
}