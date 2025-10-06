package com.utephonehub.controller;

import com.utephonehub.service.UserService;
import com.utephonehub.dto.request.LoginRequest;
import com.utephonehub.dto.request.RegisterRequest;
import com.utephonehub.dto.response.LoginResponse;
import com.utephonehub.dto.response.UserResponse;
import com.utephonehub.exception.BusinessException;
import com.utephonehub.exception.ValidationException;
import com.utephonehub.util.JsonUtil;
import com.utephonehub.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Authentication Controller
 * Xử lý các request liên quan đến authentication
 */
// @WebServlet("/api/v1/auth/*") // Using web.xml mapping instead
public class AuthController extends HttpServlet {
    
    private static final Logger logger = LogManager.getLogger(AuthController.class);
    private final UserService userService;
    private final JsonUtil jsonUtil;
    private final JwtUtil jwtUtil;
    
    public AuthController() {
        this.userService = new UserService();
        this.jsonUtil = new JsonUtil();
        this.jwtUtil = new JwtUtil();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        logger.info("AuthController GET request: {}", pathInfo);
        
        try {
            switch (pathInfo) {
                case "/login":
                    // Forward to login page
                    request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
                    break;
                case "/register":
                    // Forward to register page (you can create this later)
                    request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
                    break;
                default:
                    sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, "Page not found");
            }
        } catch (Exception e) {
            logger.error("Error in doGet", e);
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        logger.info("AuthController POST request: {}", pathInfo);
        
        try {
            switch (pathInfo) {
                case "/register":
                    handleRegister(request, response);
                    break;
                case "/login":
                    handleLogin(request, response);
                    break;
                default:
                    sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, "Endpoint not found");
            }
        } catch (Exception e) {
            logger.error("Error in AuthController", e);
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }
    
    /**
     * Xử lý đăng ký user
     */
    private void handleRegister(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        try {
            // Parse request body
            RegisterRequest registerRequest = jsonUtil.parseJson(request, RegisterRequest.class);
            
            // Register user
            LoginResponse loginResponse = userService.register(registerRequest);
            
            // Send success response
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("success", true);
            responseData.put("message", "Đăng ký tài khoản thành công");
            responseData.put("data", loginResponse);
            
            sendJsonResponse(response, HttpServletResponse.SC_CREATED, responseData);
            
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("validation")) {
                logger.warn("Validation error in register: {}", e.getMessage());
                sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            } else if (e.getMessage() != null && e.getMessage().contains("đã tồn tại")) {
                logger.warn("Business error in register: {}", e.getMessage());
                sendErrorResponse(response, HttpServletResponse.SC_CONFLICT, e.getMessage());
            } else {
                logger.error("Unexpected error in register: " + e.getMessage(), e);
                sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Đã xảy ra lỗi hệ thống");
            }
        }
    }
    
    /**
     * Xử lý đăng nhập user
     */
    private void handleLogin(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        try {
            // Parse request body
            LoginRequest loginRequest = jsonUtil.parseJson(request, LoginRequest.class);
            
            // Login user
            LoginResponse loginResponse = userService.login(loginRequest);
            
            // Send success response
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("success", true);
            responseData.put("message", "Đăng nhập thành công");
            responseData.put("data", loginResponse);
            
            sendJsonResponse(response, HttpServletResponse.SC_OK, responseData);
            
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("validation")) {
                logger.warn("Validation error in login: {}", e.getMessage());
                sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            } else if (e.getMessage() != null && (e.getMessage().contains("không đúng") || e.getMessage().contains("khóa"))) {
                logger.warn("Business error in login: {}", e.getMessage());
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            } else {
                logger.error("Unexpected error in login: " + e.getMessage(), e);
                sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Đã xảy ra lỗi hệ thống");
            }
        }
    }
    
    /**
     * Gửi JSON response thành công
     */
    private void sendJsonResponse(HttpServletResponse response, int statusCode, Object data) 
            throws IOException {
        
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String jsonResponse = jsonUtil.toJson(data);
        response.getWriter().write(jsonResponse);
        
        logger.debug("Sent JSON response: {}", jsonResponse);
    }
    
    /**
     * Gửi error response
     */
    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) 
            throws IOException {
        
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("message", message);
        
        String jsonResponse = jsonUtil.toJson(errorResponse);
        response.getWriter().write(jsonResponse);
        
        logger.debug("Sent error response: {}", jsonResponse);
    }
}
