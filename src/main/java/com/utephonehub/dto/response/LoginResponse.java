package com.utephonehub.dto.response;

/**
 * Login Response DTO
 * DTO cho response đăng nhập
 */
public class LoginResponse {
    
    private String accessToken;
    private UserResponse user;
    
    // Constructors
    public LoginResponse() {}
    
    public LoginResponse(String accessToken, UserResponse user) {
        this.accessToken = accessToken;
        this.user = user;
    }
    
    // Getters and Setters
    public String getAccessToken() {
        return accessToken;
    }
    
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public UserResponse getUser() {
        return user;
    }
    
    public void setUser(UserResponse user) {
        this.user = user;
    }
    
    @Override
    public String toString() {
        return "LoginResponse{" +
                "accessToken='[PROTECTED]'" +
                ", user=" + user +
                '}';
    }
}
