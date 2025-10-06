// Toggle between login and register forms
const container = document.querySelector(".container");
const registerBtn = document.querySelector(".register-btn");
const loginBtn = document.querySelector(".login-btn");

registerBtn.addEventListener("click", () => {
  container.classList.add("active");
});

loginBtn.addEventListener("click", () => {
  container.classList.remove("active");
});

// Utility functions
function showMessage(elementId, message, isError = false) {
  const element = document.getElementById(elementId);
  element.textContent = message;
  element.style.display = "block";

  // Hide after 5 seconds
  setTimeout(() => {
    element.style.display = "none";
  }, 5000);
}

function hideMessage(elementId) {
  document.getElementById(elementId).style.display = "none";
}

// Login form handler
document
  .getElementById("loginForm")
  .addEventListener("submit", async function (e) {
    e.preventDefault();

    hideMessage("errorMessage");
    hideMessage("successMessage");

    const formData = new FormData(this);
    const loginData = {
      username: formData.get("username"),
      password: formData.get("password"),
    };

    // Validate inputs
    if (!loginData.username || !loginData.password) {
      showMessage("errorMessage", "Vui lòng nhập đầy đủ thông tin!", true);
      return;
    }

    try {
      const response = await fetch("/api/v1/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(loginData),
      });

      const data = await response.json();

      if (data.success) {
        // Store token and user info
        localStorage.setItem("accessToken", data.data.accessToken);
        localStorage.setItem("user", JSON.stringify(data.data.user));

        showMessage("successMessage", "Đăng nhập thành công!");

        // Redirect to previous page or home after 1 second
        setTimeout(() => {
          const returnUrl = new URLSearchParams(window.location.search).get(
            "returnUrl"
          );
          window.location.href = returnUrl || "/";
        }, 1000);
      } else {
        showMessage(
          "errorMessage",
          data.message || "Đăng nhập thất bại!",
          true
        );
      }
    } catch (error) {
      console.error("Login error:", error);
      showMessage("errorMessage", "Có lỗi xảy ra, vui lòng thử lại!", true);
    }
  });

// Register form handler
document
  .getElementById("registerForm")
  .addEventListener("submit", async function (e) {
    e.preventDefault();

    hideMessage("regErrorMessage");
    hideMessage("regSuccessMessage");

    const formData = new FormData(this);
    const registerData = {
      username: formData.get("username"),
      fullName: formData.get("fullName"),
      email: formData.get("email"),
      password: formData.get("password"),
      confirmPassword: formData.get("confirmPassword"),
    };

    // Validate inputs
    if (
      !registerData.username ||
      !registerData.fullName ||
      !registerData.email ||
      !registerData.password ||
      !registerData.confirmPassword
    ) {
      showMessage("regErrorMessage", "Vui lòng nhập đầy đủ thông tin!", true);
      return;
    }

    if (registerData.password !== registerData.confirmPassword) {
      showMessage("regErrorMessage", "Mật khẩu xác nhận không khớp!", true);
      return;
    }

    if (registerData.password.length < 6) {
      showMessage("regErrorMessage", "Mật khẩu phải có ít nhất 6 ký tự!", true);
      return;
    }

    // Email validation
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(registerData.email)) {
      showMessage("regErrorMessage", "Email không hợp lệ!", true);
      return;
    }

    try {
      const response = await fetch("/api/v1/auth/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          username: registerData.username,
          fullName: registerData.fullName,
          email: registerData.email,
          password: registerData.password,
        }),
      });

      const data = await response.json();

      if (data.success) {
        showMessage(
          "regSuccessMessage",
          "Đăng ký thành công! Chuyển sang đăng nhập..."
        );

        // Clear form
        this.reset();

        // Switch to login form after 2 seconds
        setTimeout(() => {
          container.classList.remove("active");
        }, 2000);
      } else {
        showMessage(
          "regErrorMessage",
          data.message || "Đăng ký thất bại!",
          true
        );
      }
    } catch (error) {
      console.error("Register error:", error);
      showMessage("regErrorMessage", "Có lỗi xảy ra, vui lòng thử lại!", true);
    }
  });

// Auto-hide messages when user starts typing
document.querySelectorAll("input").forEach((input) => {
  input.addEventListener("input", function () {
    const form = this.closest("form");
    if (form.id === "loginForm") {
      hideMessage("errorMessage");
      hideMessage("successMessage");
    } else if (form.id === "registerForm") {
      hideMessage("regErrorMessage");
      hideMessage("regSuccessMessage");
    }
  });
});
