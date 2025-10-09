package com.utephonehub.filter;

import com.utephonehub.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * JWT Authentication Filter
 * Filter để xác thực JWT token
 */
@WebFilter("/api/v1/*")
public class JwtAuthenticationFilter implements Filter {
    
    private static final Logger logger = LogManager.getLogger(JwtAuthenticationFilter.class);
    private final JwtUtil jwtUtil;
    
    // Các endpoint không cần authentication
    private static final String[] PUBLIC_ENDPOINTS = {
        "/api/v1/auth/register",
        "/api/v1/auth/login",
        "/api/v1/auth/forgot-password/*",  // All forgot-password sub-paths
        "/api/v1/auth/reset-password",
        "/api/v1/auth/refresh",
        "/api/v1/health",
        "/api/v1/location/*"  // All location endpoints
    };
    
    public JwtAuthenticationFilter() {
        this.jwtUtil = new JwtUtil();
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("JWT Authentication Filter initialized");
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // Get request path without context path
        String contextPath = httpRequest.getContextPath();
        String requestURI = httpRequest.getRequestURI();
        String path = requestURI.substring(contextPath.length());
        String method = httpRequest.getMethod();
        
        logger.debug("JWT Filter - {} {} (context: {}, full URI: {})", method, path, contextPath, requestURI);
        
        // Kiểm tra xem endpoint có public không
        if (isPublicEndpoint(path)) {
            logger.debug("Public endpoint, skipping authentication: {}", path);
            chain.doFilter(request, response);
            return;
        }
        
        // Lấy token từ Authorization header
        String authHeader = httpRequest.getHeader("Authorization");
        String token = jwtUtil.extractTokenFromHeader(authHeader);
        
        if (token == null) {
            logger.warn("No JWT token found in request: {}", path);
            sendUnauthorizedResponse(httpResponse, "Access token is required");
            return;
        }
        
        // Validate token
        if (!jwtUtil.validateToken(token)) {
            logger.warn("Invalid JWT token for request: {}", requestURI);
            sendUnauthorizedResponse(httpResponse, "Invalid or expired access token");
            return;
        }
        
        // Kiểm tra token có hết hạn không
        if (jwtUtil.isTokenExpired(token)) {
            logger.warn("Expired JWT token for request: {}", requestURI);
            sendUnauthorizedResponse(httpResponse, "Access token has expired");
            return;
        }
        
        // Lấy email từ token và set vào request attribute
        try {
            String email = jwtUtil.getEmailFromToken(token);
            httpRequest.setAttribute("currentUserEmail", email);
            logger.debug("User authenticated: {}", email);
        } catch (Exception e) {
            logger.error("Error extracting email from token", e);
            sendUnauthorizedResponse(httpResponse, "Invalid access token");
            return;
        }
        
        // Tiếp tục filter chain
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
        logger.info("JWT Authentication Filter destroyed");
    }
    
    /**
     * Kiểm tra endpoint có public không
     * @param requestURI Request URI
     * @return true nếu là public endpoint
     */
    private boolean isPublicEndpoint(String requestURI) {
        for (String endpoint : PUBLIC_ENDPOINTS) {
            // Exact match
            if (requestURI.equals(endpoint)) {
                return true;
            }
            // Wildcard pattern matching (support /* suffix)
            if (endpoint.endsWith("/*") && requestURI.startsWith(endpoint.substring(0, endpoint.length() - 1))) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Gửi response 401 Unauthorized
     * @param response HttpServletResponse
     * @param message Error message
     * @throws IOException
     */
    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String jsonResponse = String.format(
            "{\"success\": false, \"message\": \"%s\"}", message
        );
        
        response.getWriter().write(jsonResponse);
    }
}
