package com.utephonehub.service;

import com.utephonehub.config.DatabaseConfig;
import com.utephonehub.entity.User;
import com.utephonehub.repository.UserRepository;
import com.utephonehub.util.PasswordUtil;
import com.utephonehub.util.JwtUtil;
import com.utephonehub.dto.request.LoginRequest;
import com.utephonehub.dto.request.RegisterRequest;
import com.utephonehub.dto.response.LoginResponse;
import com.utephonehub.dto.response.UserResponse;
import com.utephonehub.exception.BusinessException;
import com.utephonehub.exception.ValidationException;
import jakarta.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * User Service
 * Xử lý business logic cho User
 */
public class UserService {
    
    private static final Logger logger = LogManager.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordUtil passwordUtil;
    private final JwtUtil jwtUtil;
    
    public UserService() {
        this.userRepository = new UserRepository();
        this.passwordUtil = new PasswordUtil();
        this.jwtUtil = new JwtUtil();
    }
    
    /**
     * Đăng ký user mới
     * @param request RegisterRequest
     * @return LoginResponse với access token
     */
    public LoginResponse register(RegisterRequest request) throws RuntimeException {
        logger.info("Registering new user: {}", request.getEmail());
        
        // Validate input
        validateRegisterRequest(request);
        
        // Kiểm tra email đã tồn tại chưa
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email đã tồn tại trong hệ thống");
        }
        
        // Tạo user mới
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordUtil.hashPassword(request.getPassword()));
        user.setRole(User.UserRole.CUSTOMER);
        user.setStatus(User.UserStatus.ACTIVE);
        
        // Lưu user
        user = userRepository.save(user);
        
        // Tạo access token
        String accessToken = jwtUtil.generateToken(user.getEmail());
        
        logger.info("User registered successfully: {}", user.getEmail());
        
        return new LoginResponse(
            accessToken,
            convertToUserResponse(user)
        );
    }
    
    /**
     * Đăng nhập user
     * @param request LoginRequest
     * @return LoginResponse với access token
     */
    public LoginResponse login(LoginRequest request) throws RuntimeException {
        logger.info("User login attempt: {}", request.getEmail());
        
        // Validate input
        validateLoginRequest(request);
        
        // Tìm user theo email
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Email hoặc mật khẩu không đúng");
        }
        
        User user = userOpt.get();
        
        // Kiểm tra trạng thái user
        if (user.getStatus() != User.UserStatus.ACTIVE) {
            throw new RuntimeException("Tài khoản đã bị khóa hoặc chưa được kích hoạt");
        }
        
        // Kiểm tra mật khẩu
        if (!passwordUtil.verifyPassword(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Email hoặc mật khẩu không đúng");
        }
        
        // Tạo access token
        String accessToken = jwtUtil.generateToken(user.getEmail());
        
        logger.info("User logged in successfully: {}", user.getEmail());
        
        return new LoginResponse(
            accessToken,
            convertToUserResponse(user)
        );
    }
    
    /**
     * Lấy thông tin user theo ID
     * @param id User ID
     * @return UserResponse
     */
    public UserResponse getUserById(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Không tìm thấy user");
        }
        
        return convertToUserResponse(userOpt.get());
    }
    
    /**
     * Lấy thông tin user theo email
     * @param email User email
     * @return UserResponse
     */
    public UserResponse getUserByEmail(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Không tìm thấy user");
        }
        
        return convertToUserResponse(userOpt.get());
    }
    
    /**
     * Lấy tất cả users (Admin only)
     * @return List<UserResponse>
     */
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Lấy users với pagination (Admin only)
     * @param page Số trang
     * @param size Kích thước trang
     * @return List<UserResponse>
     */
    public List<UserResponse> getUsers(int page, int size) {
        List<User> users = userRepository.findAll(page, size);
        return users.stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Cập nhật trạng thái user (Admin only)
     * @param id User ID
     * @param status Trạng thái mới
     * @return true nếu cập nhật thành công
     */
    public boolean updateUserStatus(Long id, User.UserStatus status) {
        return userRepository.updateStatus(id, status);
    }
    
    /**
     * Xóa user (Admin only)
     * @param id User ID
     * @return true nếu xóa thành công
     */
    public boolean deleteUser(Long id) {
        return userRepository.deleteById(id);
    }
    
    /**
     * Đếm tổng số users
     * @return Tổng số users
     */
    public long countUsers() {
        return userRepository.count();
    }
    
    /**
     * Validate register request
     * @param request RegisterRequest
     */
    private void validateRegisterRequest(RegisterRequest request) {
        if (request.getFullName() == null || request.getFullName().trim().isEmpty()) {
            throw new RuntimeException("validation: Họ và tên không được để trống");
        }
        
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new RuntimeException("validation: Email không được để trống");
        }
        
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new RuntimeException("validation: Mật khẩu không được để trống");
        }
        
        if (!passwordUtil.isValidPassword(request.getPassword())) {
            throw new RuntimeException("validation: Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường và số");
        }
    }
    
    /**
     * Validate login request
     * @param request LoginRequest
     */
    private void validateLoginRequest(LoginRequest request) {
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new RuntimeException("validation: Email không được để trống");
        }
        
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new RuntimeException("validation: Mật khẩu không được để trống");
        }
    }
    
    /**
     * Convert User entity to UserResponse DTO
     * @param user User entity
     * @return UserResponse
     */
    private UserResponse convertToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setRole(user.getRole().name());
        response.setStatus(user.getStatus().name());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }
}
