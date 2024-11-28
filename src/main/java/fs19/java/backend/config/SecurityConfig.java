package fs19.java.backend.config;

import fs19.java.backend.application.UserDetailsServiceImpl;
import fs19.java.backend.domain.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

/**
 * Responsible to handle JWT security configuration
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtAuthFilter jwtAuthFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Profile("test")
    public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
        http.cors(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable).sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authorizeHttpRequests(auth -> {
                    // Public endpoints that can be accessed without authentication
                    auth.requestMatchers("/api/v1/**").hasAnyAuthority("TEST-USER");
                    // Block all other requests that don't match any of the above rules
                    auth.anyRequest().denyAll();  // This ensures other requests are blocked

                }).exceptionHandling(exception ->
                        exception.accessDeniedHandler(accessDeniedHandler())
                                .authenticationEntryPoint((req, res, authEx) -> {
                                    res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                    res.getWriter().write("Unauthorized access: " + authEx.getMessage());
                                })
                )

                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class).authenticationManager(authenticationManager(http));

        return http.build();
    }


    @Bean
    @Profile("!test")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        final List<SecurityRole> rolePermissions = getRolePermissionsFromDatabase();
        http.cors(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable).sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authorizeHttpRequests(auth -> {
                    // Public endpoints that can be accessed without authentication
                    auth
                            .requestMatchers("/api/v1/auth/signup", "/api/v1/auth/login").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/v1/companies").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/v1/workspaces").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                            .requestMatchers("/api/v1/accept-invitation/redirect").permitAll()
                            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll();
                    // Loop through dynamic role-permission mappings from the database
                    for (SecurityRole rolePermission : rolePermissions) {
                        // Example: check GET /api/v1/resource for specific role permissions
                        auth.requestMatchers(rolePermission.getMethod(), rolePermission.getPermission()).hasAuthority(rolePermission.getRole());
                    }

                    // Block all other requests that don't match any of the above rules
                    auth.anyRequest().denyAll();  // This ensures other requests are blocked

                }).exceptionHandling(exception ->
                        exception.accessDeniedHandler(accessDeniedHandler())
                                .authenticationEntryPoint((req, res, authEx) -> {
                                    res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                    res.getWriter().write("Unauthorized access: " + authEx.getMessage());
                                })
                )

                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class).authenticationManager(authenticationManager(http));

        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (HttpServletRequest request, HttpServletResponse response, org.springframework.security.access.AccessDeniedException accessDeniedException) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Access Denied\", \"message\": \"" + accessDeniedException.getMessage() + "\"}");
        };
    }

    private List<SecurityRole> getRolePermissionsFromDatabase() {
        return userDetailsService.findAllPermissions();
    }


    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    public static String getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String userName = null;

        if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
            userName = springSecurityUser.getUsername();
        }
        if (authentication.getPrincipal() instanceof String) {
            userName = authentication.getPrincipal().toString();
        }
        return userName;
    }

    public static User getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication.getCredentials() instanceof User) {
            return (User) authentication.getCredentials();
        }
        return new User();
    }
}