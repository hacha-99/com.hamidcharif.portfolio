package com.hamidcharif.portfolio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.hamidcharif.portfolio.security.JwtAuthFilter;
import com.hamidcharif.portfolio.security.rate_limiting.LoginRateLimitFilter;

import jakarta.servlet.http.HttpServletResponse;

/**
 * SecurityConfig.java provides configuration for endpoint authorization and
 * encoder and authentication beans.
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final LoginRateLimitFilter loginRateLimitFilter;
    private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

    // Constructor injection for required dependencies
    public SecurityConfig(LoginRateLimitFilter loginRateLimitFilter,
            JwtAuthFilter jwtAuthFilter,
            UserDetailsService userDetailsService) {
        this.loginRateLimitFilter = loginRateLimitFilter;
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Main security configuration
     * Defines endpoint access rules and JWT filter setup
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers("/csp-report"))

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\": \"unauthorized\"}");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\": \"csrf_invalid_or_missing\"}");
                        }))

                // Configure endpoint authorization
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers("/auth/login", "/csrf", "/csp-report").permitAll()

                        // Role-based endpoints
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")

                        // All other endpoints require authentication
                        .anyRequest().authenticated())

                // Stateless session (required for JWT)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Set custom authentication provider
                .authenticationProvider(authenticationProvider())

                // Add JWT filter before Spring Security's default filter
                .addFilterBefore(loginRateLimitFilter, UsernamePasswordAuthenticationFilter.class)

                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                .headers(headers -> headers
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives(
                                        "default-src 'self'; " +
                                        "script-src 'self' 'unsafe-inline'; " + // unsafe-inline for js frameworks
                                        "style-src 'self' 'unsafe-inline'; " + // unsafe-inline for ng/tailwind
                                        "img-src 'self' data: https:; " + // allow pics from own server and https
                                        "media-src 'self';" + // for self hosted videos
                                        "font-src 'self' data:; " + // needed for icons/fonts
                                        "frame-ancestors 'none';" + // self is sameOrigin iframes allowed and none is deny all
                                        "report-uri /api/csp-report;" // this needs "/api" because spring doesnt append it here from properties
                                ))
                        .frameOptions(frame -> frame.deny()) // kept for backwards compatibility
                );

        return http.build();
    }

    /**
     * Password encoder bean (uses BCrypt hashing)
     * Critical for secure password storage
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Authentication provider configuration
     * Links UserDetailsService and PasswordEncoder
     */
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Authentication manager bean
     * Required for programmatic authentication (e.g., in /createToken)
     */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
