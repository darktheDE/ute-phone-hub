<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Tài khoản của tôi - UTE Phone Hub</title>

    <!-- Favicon -->
    <link
      rel="icon"
      type="image/png"
      href="${pageContext.request.contextPath}/static/favicon.png"
    />
    <link
      rel="shortcut icon"
      type="image/png"
      href="${pageContext.request.contextPath}/static/favicon.png"
    />

    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/static/css/main.css"
    />
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/static/css/components/auth.css"
    />
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/static/css/pages/profile.css"
    />

    <!-- Google Fonts - Roboto -->
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap"
      rel="stylesheet"
    />

    <!-- Choices.js for searchable select -->
    <link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/choices.js@10.2.0/public/assets/styles/choices.min.css"
    />

    <link
      href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css"
      rel="stylesheet"
    />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
    />
  </head>
  <body>
    <!-- Profile Header (không dùng header chung) -->
    <div class="profile-header">
      <div class="profile-header-content">
        <!-- Logo -->
        <a href="${pageContext.request.contextPath}/" class="profile-logo">
          <div class="logo-icon">U</div>
          <span class="logo-text">UTE Phone Hub</span>
        </a>

        <!-- Page Title -->
        <div class="profile-title">
          <i class="fas fa-user-circle"></i>
          <h1>Tài khoản của tôi</h1>
        </div>

        <!-- Actions -->
        <div class="profile-header-actions">
          <a href="${pageContext.request.contextPath}/" class="btn-back">
            <i class="fas fa-arrow-left"></i>
            <span>Quay lại</span>
          </a>
          <button class="btn-logout" id="logoutBtn">
            <i class="fas fa-sign-out-alt"></i>
            <span>Đăng xuất</span>
          </button>
        </div>
      </div>
    </div>

    <div class="profile-container">
      <div class="sidebar">
        <div class="user-info">
          <div class="avatar">
            <i class="bx bxs-user-circle"></i>
          </div>
          <h3 id="userName">Loading...</h3>
          <p id="userEmail">Loading...</p>
        </div>
        <nav class="sidebar-nav">
          <a href="#" class="nav-item active" data-tab="info">
            <i class="bx bxs-user"></i>
            <span>Thông tin cá nhân</span>
          </a>
          <a href="#" class="nav-item" data-tab="password">
            <i class="bx bxs-lock-alt"></i>
            <span>Đổi mật khẩu</span>
          </a>
          <a href="#" class="nav-item" data-tab="addresses">
            <i class="bx bxs-map"></i>
            <span>Địa chỉ giao hàng</span>
          </a>
          <a href="#" class="nav-item" data-tab="orders">
            <i class="bx bxs-shopping-bag"></i>
            <span>Đơn hàng của tôi</span>
          </a>
        </nav>
      </div>

      <div class="content">
        <!-- Tab: Thông tin cá nhân -->
        <div class="tab-content active" id="tab-info">
          <h2>Thông tin cá nhân</h2>
          <form id="updateProfileForm">
            <div class="form-group">
              <label>Họ và tên</label>
              <input type="text" id="fullName" name="fullName" required />
            </div>
            <div class="form-group">
              <label>Số điện thoại</label>
              <input type="tel" id="phoneNumber" name="phoneNumber" />
            </div>
            <div class="form-group">
              <label>Email</label>
              <input type="email" id="email" disabled />
            </div>
            <button type="submit" class="btn btn-primary">Cập nhật</button>
            <div class="message" id="profileMessage"></div>
          </form>
        </div>

        <!-- Tab: Đổi mật khẩu -->
        <div class="tab-content" id="tab-password">
          <h2>Đổi mật khẩu</h2>
          <form id="changePasswordForm">
            <div class="form-group">
              <label>Mật khẩu cũ</label>
              <input
                type="password"
                id="oldPassword"
                name="oldPassword"
                required
              />
            </div>
            <div class="form-group">
              <label>Mật khẩu mới</label>
              <input
                type="password"
                id="newPassword"
                name="newPassword"
                required
              />
            </div>
            <div class="form-group">
              <label for="profileConfirmPassword">Xác nhận mật khẩu mới</label>
              <input
                type="password"
                id="profileConfirmPassword"
                name="confirmPassword"
                required
              />
            </div>
            <button type="submit" class="btn btn-primary">Đổi mật khẩu</button>
            <div class="message" id="passwordMessage"></div>
          </form>
        </div>

        <!-- Tab: Địa chỉ giao hàng -->
        <div class="tab-content" id="tab-addresses">
          <div class="address-header">
            <h2>Địa chỉ giao hàng</h2>
            <button class="btn btn-primary" id="btnAddAddress">
              <i class="bx bx-plus"></i> Thêm địa chỉ
            </button>
          </div>
          <div id="addressList" class="address-list">
            <p class="loading">Đang tải...</p>
          </div>
        </div>

        <!-- Tab: Đơn hàng -->
        <div class="tab-content" id="tab-orders">
          <h2>Đơn hàng của tôi</h2>
          <p>Chức năng đang được phát triển...</p>
        </div>
      </div>
    </div>

    <!-- Modal: Thêm/Sửa địa chỉ -->
    <div id="addressModal" class="modal">
      <div class="modal-content">
        <span class="close">&times;</span>
        <h3 id="modalTitle">Thêm địa chỉ mới</h3>
        <form id="addressForm">
          <input type="hidden" id="addressId" />
          <div class="form-group">
            <label>Tên người nhận</label>
            <input
              type="text"
              id="recipientName"
              name="recipientName"
              required
            />
          </div>
          <div class="form-group">
            <label>Số điện thoại</label>
            <input type="tel" id="recipientPhone" name="phoneNumber" required />
          </div>
          <div class="form-group">
            <label>Tỉnh/Thành phố</label>
            <select
              id="province"
              name="province"
              class="searchable-select"
              required
            >
              <option value="">-- Chọn Tỉnh/Thành phố --</option>
            </select>
            <input type="hidden" id="provinceCode" name="provinceCode" />
          </div>
          <div class="form-group">
            <label>Xã/Phường/Thị trấn</label>
            <select id="ward" name="ward" class="searchable-select" required>
              <option value="">-- Chọn Xã/Phường --</option>
            </select>
            <input type="hidden" id="wardCode" name="wardCode" />
          </div>
          <div class="form-group">
            <label>Địa chỉ cụ thể</label>
            <input
              type="text"
              id="streetAddress"
              name="streetAddress"
              placeholder="Số nhà, tên đường..."
              required
            />
          </div>
          <div class="form-group checkbox-group">
            <label>
              <input type="checkbox" id="isDefault" name="isDefault" />
              Đặt làm địa chỉ mặc định
            </label>
          </div>
          <button type="submit" class="btn btn-primary">Lưu</button>
          <div class="message" id="addressFormMessage"></div>
        </form>
      </div>
    </div>

    <!-- Choices.js for searchable select -->
    <script src="https://cdn.jsdelivr.net/npm/choices.js@10.2.0/public/assets/scripts/choices.min.js"></script>

    <script src="${pageContext.request.contextPath}/static/js/profile.js"></script>

    <!-- Logout Handler -->
    <script>
      document
        .getElementById("logoutBtn")
        .addEventListener("click", function () {
          // Use global logout function from auth.js
          if (typeof logout === "function") {
            logout();
          } else {
            // Fallback: clear storage and redirect
            localStorage.removeItem("accessToken");
            localStorage.removeItem("refreshToken");
            localStorage.removeItem("user");
            window.location.href = "${pageContext.request.contextPath}/";
          }
        });
    </script>
  </body>
</html>
