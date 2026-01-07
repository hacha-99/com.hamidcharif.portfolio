package com.hamidcharif.portfolio.security.rate_limiting;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginRateLimitFilter extends OncePerRequestFilter {

    private final LoginRateLimit rateLimiter;

    public LoginRateLimitFilter(LoginRateLimit rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        if (isLoginRequest(request)) {

            // TODO when using traefik, original remote ip should be forwarded
            String ip = request.getRemoteAddr();
            Bucket bucket = rateLimiter.resolveBucket(ip);

            if (!bucket.tryConsume(1)) {
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isLoginRequest(HttpServletRequest request) {
        return request.getRequestURI().equals("/auth/login")
            && request.getMethod().equalsIgnoreCase("POST");
    }
}