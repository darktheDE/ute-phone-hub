// Profile Page JavaScript - Complete Rewrite
const API_BASE_URL = "/api/v1";

// ==================== UTILITY FUNCTIONS ====================

// Get authentication token
function getToken() {
  return localStorage.getItem("accessToken");
}

// Check authentication status
function checkAuth() {
  const token = getToken();
  if (!token) {
    window.location.href = "/login";
    return false;
  }
  return true;
}

// Fetch API with authentication
async function fetchAPI(url, options = {}) {
  const token = getToken();
  const headers = {
    "Content-Type": "application/json",
    ...options.headers,
  };

  if (token) {
    headers["Authorization"] = `Bearer ${token}`;
  }

  try {
    const response = await fetch(url, {
      ...options,
      headers,
    });

    // Handle unauthorized
    if (response.status === 401) {
      localStorage.removeItem("accessToken");
      localStorage.removeItem("user");
      window.location.href = "/login";
      return null;
    }

    return response;
  } catch (error) {
    console.error("Fetch error:", error);
    throw error;
  }
}

// Show message helper
function showMessage(elementId, message, isSuccess = true) {
  const messageEl = document.getElementById(elementId);
  if (!messageEl) return;

  messageEl.textContent = message;
  messageEl.className = `message ${isSuccess ? "success" : "error"}`;
  messageEl.style.display = "block";

  setTimeout(() => {
    messageEl.style.display = "none";
  }, 5000);
}

// ==================== PROFILE FUNCTIONS ====================

// Load user profile data
async function loadProfile() {
  console.log("Loading profile...");

  try {
    const response = await fetchAPI(`${API_BASE_URL}/user/me`);

    if (!response) {
      console.error("No response from API");
      return;
    }

    const result = await response.json();
    console.log("Profile API Response:", result);

    if (result.success && result.data) {
      const user = result.data;
      console.log("User data:", user);

      // Update sidebar user info
      const userNameEl = document.getElementById("userName");
      const userEmailEl = document.getElementById("userEmail");

      if (userNameEl) {
        userNameEl.textContent = user.fullName || user.email.split("@")[0];
      }
      if (userEmailEl) {
        userEmailEl.textContent = user.email;
      }

      // Update form fields
      const fullNameInput = document.getElementById("fullName");
      const phoneNumberInput = document.getElementById("phoneNumber");
      const emailInput = document.getElementById("email");

      if (fullNameInput) fullNameInput.value = user.fullName || "";
      if (phoneNumberInput) phoneNumberInput.value = user.phoneNumber || "";
      if (emailInput) emailInput.value = user.email || "";

      console.log("✅ Profile loaded successfully");
    } else {
      console.error("❌ API error:", result.message || "Unknown error");
      showMessage(
        "profileMessage",
        result.message || "Không thể tải thông tin",
        false
      );
    }
  } catch (error) {
    console.error("❌ Error loading profile:", error);
    showMessage("profileMessage", "Lỗi khi tải thông tin người dùng", false);
  }
}

// Update profile
async function updateProfile(formData) {
  try {
    const response = await fetchAPI(`${API_BASE_URL}/user/profile`, {
      method: "POST",
      body: JSON.stringify(formData),
    });

    if (!response) return;

    const result = await response.json();
    console.log("Update profile response:", result);

    if (result.success) {
      showMessage(
        "profileMessage",
        "Cập nhật thông tin thành công! Đang tải lại trang...",
        true
      );

      // Reload page after 1 second to ensure data consistency
      setTimeout(() => {
        window.location.reload();
      }, 1000);
    } else {
      showMessage(
        "profileMessage",
        result.message || "Cập nhật thất bại",
        false
      );
    }
  } catch (error) {
    console.error("Error updating profile:", error);
    showMessage("profileMessage", "Có lỗi xảy ra khi cập nhật", false);
  }
}

// Change password
async function changePassword(formData) {
  try {
    const response = await fetchAPI(`${API_BASE_URL}/user/password`, {
      method: "POST",
      body: JSON.stringify(formData),
    });

    if (!response) return;

    const result = await response.json();
    console.log("Change password response:", result);

    if (result.success) {
      showMessage("passwordMessage", "Đổi mật khẩu thành công!", true);
      // Clear password fields
      document.getElementById("changePasswordForm").reset();
    } else {
      showMessage(
        "passwordMessage",
        result.message || "Đổi mật khẩu thất bại",
        false
      );
    }
  } catch (error) {
    console.error("Error changing password:", error);
    showMessage("passwordMessage", "Có lỗi xảy ra khi đổi mật khẩu", false);
  }
}

// ==================== ADDRESS FUNCTIONS ====================

// Load addresses
async function loadAddresses() {
  console.log("Loading addresses...");

  try {
    const response = await fetchAPI(`${API_BASE_URL}/user/addresses`);

    if (!response) return;

    const result = await response.json();
    console.log("Addresses response:", result);

    const addressList = document.getElementById("addressList");
    if (!addressList) return;

    if (result.success && result.data && result.data.length > 0) {
      // Sort addresses: Default address first, then others
      const sortedAddresses = result.data.sort((a, b) => {
        if (a.isDefault && !b.isDefault) return -1;
        if (!a.isDefault && b.isDefault) return 1;
        return 0;
      });

      addressList.innerHTML = sortedAddresses
        .map(
          (address) => `
        <div class="address-card ${address.isDefault ? "default" : ""}">
          ${
            address.isDefault
              ? '<span class="default-badge">Mặc định</span>'
              : ""
          }
          <h4>${address.recipientName}</h4>
          <p><i class='bx bx-phone'></i> ${address.phoneNumber}</p>
          <p><i class='bx bx-map'></i> ${address.streetAddress}, ${
            address.city
          }</p>
          <div class="address-actions">
            <button class="btn btn-sm btn-primary" onclick="editAddress(${
              address.id
            })">
              <i class='bx bx-edit'></i> Sửa
            </button>
            ${
              !address.isDefault
                ? `<button class="btn btn-sm btn-danger" onclick="deleteAddress(${address.id})">
                <i class='bx bx-trash'></i> Xóa
              </button>`
                : ""
            }
          </div>
        </div>
      `
        )
        .join("");
    } else {
      addressList.innerHTML =
        '<p class="no-data">Chưa có địa chỉ nào. Vui lòng thêm địa chỉ giao hàng.</p>';
    }
  } catch (error) {
    console.error("Error loading addresses:", error);
    const addressList = document.getElementById("addressList");
    if (addressList) {
      addressList.innerHTML =
        '<p class="error">Không thể tải danh sách địa chỉ</p>';
    }
  }
}

// Add address
async function addAddress(formData) {
  try {
    const response = await fetchAPI(`${API_BASE_URL}/user/addresses`, {
      method: "POST",
      body: JSON.stringify(formData),
    });

    if (!response) return;

    const result = await response.json();
    console.log("Add address response:", result);

    if (result.success) {
      showMessage("addressFormMessage", "Thêm địa chỉ thành công!", true);
      closeModal();
      await loadAddresses();
    } else {
      showMessage(
        "addressFormMessage",
        result.message || "Thêm địa chỉ thất bại",
        false
      );
    }
  } catch (error) {
    console.error("Error adding address:", error);
    showMessage("addressFormMessage", "Có lỗi xảy ra khi thêm địa chỉ", false);
  }
}

// Update address
async function updateAddress(id, formData) {
  try {
    const response = await fetchAPI(`${API_BASE_URL}/user/addresses/${id}`, {
      method: "PUT",
      body: JSON.stringify(formData),
    });

    if (!response) return;

    const result = await response.json();
    console.log("Update address response:", result);

    if (result.success) {
      showMessage("addressFormMessage", "Cập nhật địa chỉ thành công!", true);
      closeModal();
      await loadAddresses();
    } else {
      showMessage(
        "addressFormMessage",
        result.message || "Cập nhật địa chỉ thất bại",
        false
      );
    }
  } catch (error) {
    console.error("Error updating address:", error);
    showMessage(
      "addressFormMessage",
      "Có lỗi xảy ra khi cập nhật địa chỉ",
      false
    );
  }
}

// Delete address
async function deleteAddress(id) {
  if (!confirm("Bạn có chắc chắn muốn xóa địa chỉ này?")) {
    return;
  }

  try {
    const response = await fetchAPI(`${API_BASE_URL}/user/addresses/${id}`, {
      method: "DELETE",
    });

    if (!response) return;

    const result = await response.json();
    console.log("Delete address response:", result);

    if (result.success) {
      await loadAddresses();
    } else {
      alert(result.message || "Xóa địa chỉ thất bại");
    }
  } catch (error) {
    console.error("Error deleting address:", error);
    alert("Có lỗi xảy ra khi xóa địa chỉ");
  }
}

// Edit address
async function editAddress(id) {
  try {
    const response = await fetchAPI(`${API_BASE_URL}/user/addresses`);

    if (!response) return;

    const result = await response.json();

    if (result.success && result.data) {
      const address = result.data.find((a) => a.id === id);
      if (address) {
        // Fill form with address data
        document.getElementById("addressId").value = address.id;
        document.getElementById("recipientName").value = address.recipientName;
        document.getElementById("recipientPhone").value = address.phoneNumber;
        document.getElementById("streetAddress").value = address.streetAddress;
        document.getElementById("city").value = address.city;
        document.getElementById("isDefault").checked = address.isDefault;

        // Update modal title
        document.getElementById("modalTitle").textContent = "Sửa địa chỉ";

        // Show modal
        openModal();
      }
    }
  } catch (error) {
    console.error("Error loading address for edit:", error);
  }
}

// ==================== MODAL FUNCTIONS ====================

function openModal() {
  const modal = document.getElementById("addressModal");
  if (modal) {
    modal.classList.add("show");
    modal.style.display = "flex";
  }
}

function closeModal() {
  const modal = document.getElementById("addressModal");
  if (modal) {
    modal.classList.remove("show");
    modal.style.display = "none";
    document.getElementById("addressForm").reset();
    document.getElementById("addressId").value = "";
    document.getElementById("modalTitle").textContent = "Thêm địa chỉ mới";

    // Clear any error messages
    const messageEl = document.getElementById("addressFormMessage");
    if (messageEl) {
      messageEl.style.display = "none";
    }
  }
}

// ==================== EVENT LISTENERS ====================

document.addEventListener("DOMContentLoaded", function () {
  console.log("Profile page initialized");

  // Check authentication
  if (!checkAuth()) {
    return;
  }

  // Load profile data
  loadProfile();

  // Tab switching
  const navItems = document.querySelectorAll(".nav-item");
  const tabContents = document.querySelectorAll(".tab-content");

  navItems.forEach((item) => {
    item.addEventListener("click", function (e) {
      e.preventDefault();

      const tab = this.dataset.tab;
      console.log("Switching to tab:", tab);

      // Update active nav item
      navItems.forEach((nav) => nav.classList.remove("active"));
      this.classList.add("active");

      // Update active tab content
      tabContents.forEach((content) => content.classList.remove("active"));
      const activeTab = document.getElementById(`tab-${tab}`);
      if (activeTab) {
        activeTab.classList.add("active");
      }

      // Load data for specific tabs
      if (tab === "addresses") {
        loadAddresses();
      }
    });
  });

  // Update profile form
  const updateProfileForm = document.getElementById("updateProfileForm");
  if (updateProfileForm) {
    updateProfileForm.addEventListener("submit", async function (e) {
      e.preventDefault();

      const formData = {
        fullName: document.getElementById("fullName").value,
        phoneNumber: document.getElementById("phoneNumber").value,
      };

      console.log("Updating profile with data:", formData);
      await updateProfile(formData);
    });
  }

  // Change password form
  const changePasswordForm = document.getElementById("changePasswordForm");
  if (changePasswordForm) {
    changePasswordForm.addEventListener("submit", async function (e) {
      e.preventDefault();

      const oldPassword = document.getElementById("oldPassword").value;
      const newPassword = document.getElementById("newPassword").value;
      const confirmPassword = document.getElementById("confirmPassword").value;

      // Validate passwords
      if (newPassword !== confirmPassword) {
        showMessage("passwordMessage", "Mật khẩu xác nhận không khớp!", false);
        return;
      }

      if (newPassword.length < 6) {
        showMessage(
          "passwordMessage",
          "Mật khẩu phải có ít nhất 6 ký tự!",
          false
        );
        return;
      }

      const formData = {
        oldPassword,
        newPassword,
      };

      console.log("Changing password...");
      await changePassword(formData);
    });
  }

  // Add address button
  const btnAddAddress = document.getElementById("btnAddAddress");
  if (btnAddAddress) {
    btnAddAddress.addEventListener("click", function () {
      document.getElementById("addressForm").reset();
      document.getElementById("addressId").value = "";
      document.getElementById("modalTitle").textContent = "Thêm địa chỉ mới";
      openModal();
    });
  }

  // Address form submit
  const addressForm = document.getElementById("addressForm");
  if (addressForm) {
    addressForm.addEventListener("submit", async function (e) {
      e.preventDefault();

      const addressId = document.getElementById("addressId").value;
      const formData = {
        recipientName: document.getElementById("recipientName").value,
        phoneNumber: document.getElementById("recipientPhone").value,
        streetAddress: document.getElementById("streetAddress").value,
        city: document.getElementById("city").value,
        isDefault: document.getElementById("isDefault").checked,
      };

      console.log("Address form data:", formData, "ID:", addressId);

      if (addressId) {
        // Update existing address
        await updateAddress(addressId, formData);
      } else {
        // Add new address
        await addAddress(formData);
      }
    });
  }

  // Close modal
  const closeBtn = document.querySelector(".close");
  if (closeBtn) {
    closeBtn.addEventListener("click", closeModal);
  }

  // Close modal when clicking outside
  const modal = document.getElementById("addressModal");
  if (modal) {
    modal.addEventListener("click", function (e) {
      if (e.target === modal) {
        closeModal();
      }
    });
  }
});

// Make functions globally available
window.editAddress = editAddress;
window.deleteAddress = deleteAddress;

console.log("✅ Profile.js loaded successfully");
