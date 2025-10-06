<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Đăng nhập - UTE Phone Hub</title>
    
    <!-- Favicon -->
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/static/favicon.png">
    <link rel="shortcut icon" type="image/png" href="${pageContext.request.contextPath}/static/favicon.png">

    <!-- CSS -->
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/static/css/components/auth.css"
    />

    <!-- Boxicons -->
    <link
      href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css"
      rel="stylesheet"
    />

    <!-- Favicon -->
    <link
      rel="icon"
      type="image/x-icon"
      href="${pageContext.request.contextPath}/static/images/favicon.ico"
    />
  </head>
  <body>
    <!-- Back to Home Link -->
    <div class="back-to-home">
      <a href="${pageContext.request.contextPath}/">
        <i class="bx bx-arrow-back"></i>
        Về trang chủ
      </a>
    </div>

    <div class="container">
      <!-- Login Form -->
      <div class="form-box login">
        <form id="loginForm">
          <h1>Đăng nhập</h1>

          <div class="input-box">
            <input
              type="text"
              id="username"
              name="username"
              placeholder="Tên đăng nhập hoặc email"
              required
            />
            <i class="bx bxs-user"></i>
          </div>

          <div class="input-box">
            <input
              type="password"
              id="password"
              name="password"
              placeholder="Mật khẩu"
              required
            />
            <i class="bx bxs-lock-alt"></i>
          </div>

          <div class="forgot-link">
            <a
              href="${pageContext.request.contextPath}/api/v1/auth/forgot-password"
              >Quên mật khẩu?</a
            >
          </div>

          <button type="submit" class="btn">Đăng nhập</button>

          <div
            class="error-message"
            id="errorMessage"
            style="display: none"
          ></div>
          <div
            class="success-message"
            id="successMessage"
            style="display: none"
          ></div>

          <p>hoặc đăng nhập với</p>
          <div class="social-icons">
            <a href="#"><i class="bx bxl-google"></i></a>
            <a href="#"><i class="bx bxl-facebook"></i></a>
            <a href="#"><i class="bx bxl-github"></i></a>
            <a href="#"><i class="bx bxl-linkedin"></i></a>
          </div>
        </form>
      </div>

      <!-- Register Form -->
      <div class="form-box register">
        <form id="registerForm">
          <h1>Đăng ký</h1>

          <div class="input-box">
            <input
              type="text"
              id="regUsername"
              name="username"
              placeholder="Tên đăng nhập"
              required
            />
            <i class="bx bxs-user"></i>
          </div>

          <div class="input-box">
            <input
              type="text"
              id="regFullName"
              name="fullName"
              placeholder="Họ và tên"
              required
            />
            <i class="bx bxs-user-detail"></i>
          </div>

          <div class="input-box">
            <input
              type="email"
              id="regEmail"
              name="email"
              placeholder="Email"
              required
            />
            <i class="bx bxs-envelope"></i>
          </div>

          <div class="input-box">
            <input
              type="password"
              id="regPassword"
              name="password"
              placeholder="Mật khẩu"
              required
            />
            <i class="bx bxs-lock-alt"></i>
          </div>

          <div class="input-box">
            <input
              type="password"
              id="confirmPassword"
              name="confirmPassword"
              placeholder="Xác nhận mật khẩu"
              required
            />
            <i class="bx bxs-lock-alt"></i>
          </div>

          <button type="submit" class="btn">Đăng ký</button>

          <div
            class="error-message"
            id="regErrorMessage"
            style="display: none"
          ></div>
          <div
            class="success-message"
            id="regSuccessMessage"
            style="display: none"
          ></div>

          <p>hoặc đăng ký với</p>
          <div class="social-icons">
            <a href="#"><i class="bx bxl-google"></i></a>
            <a href="#"><i class="bx bxl-facebook"></i></a>
            <a href="#"><i class="bx bxl-github"></i></a>
            <a href="#"><i class="bx bxl-linkedin"></i></a>
          </div>
        </form>
      </div>

      <!-- Toggle Panel -->
      <div class="toggle-box">
        <div class="toggle-panel toggle-left">
          <h1>Xin chào!</h1>
          <p>Chưa có tài khoản?</p>
          <button class="btn register-btn">Đăng ký</button>
        </div>

        <div class="toggle-panel toggle-right">
          <h1>Chào mừng trở lại!</h1>
          <p>Đã có tài khoản?</p>
          <button class="btn login-btn">Đăng nhập</button>
        </div>
      </div>
    </div>

    <!-- JavaScript -->
    <script src="${pageContext.request.contextPath}/static/js/auth.js"></script>
  </body>
</html>
