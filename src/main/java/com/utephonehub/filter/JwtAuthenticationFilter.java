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
        "/api/v1/health"
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
        
        String requestURI = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();
        
        logger.debug("JWT Filter - {} {}", method, requestURI);
        
        // Kiểm tra xem endpoint có public không
        if (isPublicEndpoint(requestURI)) {
            logger.debug("Public endpoint, skipping authentication: {}", requestURI);
            chain.doFilter(request, response);
            return;
        }
        
        // Lấy token từ Authorization header
        String authHeader = httpRequest.getHeader("Authorization");
        String token = jwtUtil.extractTokenFromHeader(authHeader);
        
        if (token == null) {
            logger.warn("No JWT token found in request: {}", requestURI);
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
            if (requestURI.equals(endpoint)) {
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
