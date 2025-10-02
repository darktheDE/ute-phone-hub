<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Giỏ hàng - UTE Phone Hub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/components/header.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <!-- Header -->
    <header class="header">
        <div class="header-main">
            <div class="header-content">
                <a href="${pageContext.request.contextPath}/" class="logo">
                    <div class="logo-icon">U</div>
                    <span>UTE Phone Hub</span>
                </a>
                
                <div class="search-container">
                    <div class="search-bar">
                        <input type="text" class="search-input" placeholder="Tìm kiếm sản phẩm...">
                        <button class="search-btn">
                            <i class="fas fa-search"></i>
                        </button>
                    </div>
                </div>
                
                <div class="header-actions">
                    <a href="${pageContext.request.contextPath}/wishlist" class="header-action">
                        <i class="fas fa-heart"></i>
                        <span>Yêu thích</span>
                        <span class="wishlist-badge" style="display: none;">0</span>
                    </a>
                    <a href="${pageContext.request.contextPath}/cart" class="header-action active">
                        <i class="fas fa-shopping-cart"></i>
                        <span>Giỏ hàng</span>
                        <span class="cart-badge" style="display: none;">0</span>
                    </a>
                </div>
            </div>
        </div>
    </header>

    <!-- Main Content -->
    <main class="main-content">
        <div class="cart-container">
            <div class="cart-header">
                <h1>Giỏ hàng của bạn</h1>
                <div class="cart-actions">
                    <button class="btn btn-secondary" onclick="clearCart()">
                        <i class="fas fa-trash"></i>
                        Xóa tất cả
                    </button>
                    <button class="btn btn-primary" onclick="updateCart()">
                        <i class="fas fa-sync"></i>
                        Cập nhật
                    </button>
                </div>
            </div>
            
            <div class="cart-content">
                <div class="cart-items" id="cartItems">
                    <!-- Cart items will be loaded here -->
                </div>
                
                <div class="cart-summary">
                    <div class="summary-card">
                        <h3>Tóm tắt đơn hàng</h3>
                        
                        <div class="summary-row">
                            <span>Tạm tính:</span>
                            <span id="subtotal">0₫</span>
                        </div>
                        
                        <div class="summary-row">
                            <span>Phí vận chuyển:</span>
                            <span id="shipping">0₫</span>
                        </div>
                        
                        <div class="summary-row">
                            <span>Giảm giá:</span>
                            <span id="discount">-0₫</span>
                        </div>
                        
                        <div class="summary-row total">
                            <span>Tổng cộng:</span>
                            <span id="total">0₫</span>
                        </div>
                        
                        <div class="coupon-section">
                            <div class="form-group">
                                <label for="couponCode" class="form-label">Mã giảm giá</label>
                                <div class="coupon-input">
                                    <input type="text" id="couponCode" class="form-control" placeholder="Nhập mã giảm giá">
                                    <button class="btn btn-secondary" onclick="applyCoupon()">
                                        Áp dụng
                                    </button>
                                </div>
                            </div>
                        </div>
                        
                        <button class="btn btn-primary btn-lg w-100" onclick="checkout()">
                            <i class="fas fa-credit-card"></i>
                            Thanh toán
                        </button>
                        
                        <div class="payment-methods">
                            <h4>Phương thức thanh toán</h4>
                            <div class="payment-options">
                                <div class="payment-option">
                                    <i class="fas fa-credit-card"></i>
                                    <span>Thẻ tín dụng</span>
                                </div>
                                <div class="payment-option">
                                    <i class="fas fa-university"></i>
                                    <span>Chuyển khoản</span>
                                </div>
                                <div class="payment-option">
                                    <i class="fas fa-money-bill-wave"></i>
                                    <span>COD</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="empty-cart" id="emptyCart" style="display: none;">
                <div class="empty-cart-content">
                    <i class="fas fa-shopping-cart"></i>
                    <h2>Giỏ hàng trống</h2>
                    <p>Bạn chưa có sản phẩm nào trong giỏ hàng</p>
                    <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">
                        <i class="fas fa-shopping-bag"></i>
                        Mua sắm ngay
                    </a>
                </div>
            </div>
        </div>
    </main>

    <style>
        .cart-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        
        .cart-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            flex-wrap: wrap;
            gap: 20px;
        }
        
        .cart-header h1 {
            font-size: 2rem;
            font-weight: 800;
            color: #333;
        }
        
        .cart-actions {
            display: flex;
            gap: 15px;
        }
        
        .cart-content {
            display: grid;
            grid-template-columns: 1fr 400px;
            gap: 30px;
        }
        
        .cart-items {
            background: white;
            border-radius: 15px;
            padding: 20px;
            box-shadow: 0 5px 20px rgba(0,0,0,0.08);
        }
        
        .cart-item {
            display: flex;
            gap: 20px;
            padding: 20px 0;
            border-bottom: 1px solid #f0f0f0;
        }
        
        .cart-item:last-child {
            border-bottom: none;
        }
        
        .cart-item-image {
            width: 120px;
            height: 120px;
            object-fit: cover;
            border-radius: 10px;
            flex-shrink: 0;
        }
        
        .cart-item-info {
            flex: 1;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
        }
        
        .cart-item-header {
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
            margin-bottom: 10px;
        }
        
        .cart-item-name {
            font-size: 18px;
            font-weight: 700;
            color: #333;
            margin: 0;
        }
        
        .cart-item-remove {
            background: none;
            border: none;
            color: #dc3545;
            font-size: 20px;
            cursor: pointer;
            padding: 5px;
            border-radius: 50%;
            transition: all 0.3s;
        }
        
        .cart-item-remove:hover {
            background: #dc3545;
            color: white;
        }
        
        .cart-item-details {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: auto;
        }
        
        .cart-item-price {
            font-size: 20px;
            font-weight: 700;
            color: #ee4d2d;
        }
        
        .cart-item-controls {
            display: flex;
            align-items: center;
            gap: 15px;
        }
        
        .quantity-controls {
            display: flex;
            align-items: center;
            gap: 10px;
            background: #f8f9fa;
            border-radius: 8px;
            padding: 5px;
        }
        
        .quantity-btn {
            width: 30px;
            height: 30px;
            border: none;
            background: #ff6b35;
            color: white;
            border-radius: 4px;
            cursor: pointer;
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: bold;
            transition: all 0.3s;
        }
        
        .quantity-btn:hover {
            background: #ee4d2d;
        }
        
        .quantity-btn:disabled {
            background: #ccc;
            cursor: not-allowed;
        }
        
        .quantity {
            min-width: 40px;
            text-align: center;
            font-weight: 600;
            font-size: 16px;
        }
        
        .cart-summary {
            position: sticky;
            top: 20px;
            height: fit-content;
        }
        
        .summary-card {
            background: white;
            border-radius: 15px;
            padding: 25px;
            box-shadow: 0 5px 20px rgba(0,0,0,0.08);
        }
        
        .summary-card h3 {
            font-size: 20px;
            font-weight: 700;
            color: #333;
            margin-bottom: 20px;
        }
        
        .summary-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 15px;
            font-size: 16px;
        }
        
        .summary-row.total {
            font-size: 20px;
            font-weight: 700;
            color: #333;
            border-top: 2px solid #f0f0f0;
            padding-top: 15px;
            margin-top: 15px;
        }
        
        .coupon-section {
            margin: 20px 0;
            padding: 20px 0;
            border-top: 1px solid #f0f0f0;
        }
        
        .coupon-input {
            display: flex;
            gap: 10px;
        }
        
        .coupon-input input {
            flex: 1;
        }
        
        .payment-methods {
            margin-top: 25px;
            padding-top: 20px;
            border-top: 1px solid #f0f0f0;
        }
        
        .payment-methods h4 {
            font-size: 16px;
            font-weight: 700;
            color: #333;
            margin-bottom: 15px;
        }
        
        .payment-options {
            display: flex;
            flex-direction: column;
            gap: 10px;
        }
        
        .payment-option {
            display: flex;
            align-items: center;
            gap: 10px;
            padding: 10px;
            border: 2px solid #f0f0f0;
            border-radius: 8px;
            cursor: pointer;
            transition: all 0.3s;
        }
        
        .payment-option:hover {
            border-color: #ff6b35;
            background: #fff5f2;
        }
        
        .payment-option i {
            color: #ff6b35;
            font-size: 18px;
        }
        
        .empty-cart {
            text-align: center;
            padding: 60px 20px;
        }
        
        .empty-cart-content i {
            font-size: 4rem;
            color: #ccc;
            margin-bottom: 20px;
        }
        
        .empty-cart-content h2 {
            font-size: 2rem;
            font-weight: 700;
            color: #333;
            margin-bottom: 10px;
        }
        
        .empty-cart-content p {
            color: #666;
            margin-bottom: 30px;
        }
        
        @media (max-width: 768px) {
            .cart-content {
                grid-template-columns: 1fr;
            }
            
            .cart-item {
                flex-direction: column;
                gap: 15px;
            }
            
            .cart-item-image {
                width: 100%;
                height: 200px;
            }
            
            .cart-item-details {
                flex-direction: column;
                gap: 15px;
                align-items: stretch;
            }
            
            .cart-item-controls {
                justify-content: center;
            }
        }
    </style>

    <script>
        // Load cart items on page load
        document.addEventListener('DOMContentLoaded', function() {
            loadCartItems();
        });
        
        function loadCartItems() {
            const cart = JSON.parse(localStorage.getItem('cart')) || [];
            const cartItems = document.getElementById('cartItems');
            const emptyCart = document.getElementById('emptyCart');
            
            if (cart.length === 0) {
                cartItems.style.display = 'none';
                emptyCart.style.display = 'block';
                updateCartSummary();
                return;
            }
            
            cartItems.style.display = 'block';
            emptyCart.style.display = 'none';
            
            cartItems.innerHTML = cart.map(item => `
                <div class="cart-item" data-product-id="${item.id}">
                    <img src="${item.image}" alt="${item.name}" class="cart-item-image">
                    <div class="cart-item-info">
                        <div class="cart-item-header">
                            <h4 class="cart-item-name">${item.name}</h4>
                            <button class="cart-item-remove" onclick="removeFromCart('${item.id}')">
                                <i class="fas fa-times"></i>
                            </button>
                        </div>
                        <div class="cart-item-details">
                            <div class="cart-item-price">${formatPrice(item.price)}</div>
                            <div class="cart-item-controls">
                                <div class="quantity-controls">
                                    <button class="quantity-btn" onclick="updateQuantity('${item.id}', ${item.quantity - 1})" ${item.quantity <= 1 ? 'disabled' : ''}>
                                        <i class="fas fa-minus"></i>
                                    </button>
                                    <span class="quantity">${item.quantity}</span>
                                    <button class="quantity-btn" onclick="updateQuantity('${item.id}', ${item.quantity + 1})">
                                        <i class="fas fa-plus"></i>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            `).join('');
            
            updateCartSummary();
        }
        
        function updateQuantity(productId, newQuantity) {
            let cart = JSON.parse(localStorage.getItem('cart')) || [];
            const item = cart.find(item => item.id === productId);
            
            if (item) {
                if (newQuantity <= 0) {
                    removeFromCart(productId);
                } else {
                    item.quantity = newQuantity;
                    localStorage.setItem('cart', JSON.stringify(cart));
                    loadCartItems();
                    updateCartBadge();
                }
            }
        }
        
        function removeFromCart(productId) {
            let cart = JSON.parse(localStorage.getItem('cart')) || [];
            cart = cart.filter(item => item.id !== productId);
            localStorage.setItem('cart', JSON.stringify(cart));
            loadCartItems();
            updateCartBadge();
            showNotification('Đã xóa sản phẩm khỏi giỏ hàng!', 'info');
        }
        
        function clearCart() {
            if (confirm('Bạn có chắc chắn muốn xóa tất cả sản phẩm khỏi giỏ hàng?')) {
                localStorage.removeItem('cart');
                loadCartItems();
                updateCartBadge();
                showNotification('Đã xóa tất cả sản phẩm!', 'info');
            }
        }
        
        function updateCart() {
            loadCartItems();
            showNotification('Đã cập nhật giỏ hàng!', 'success');
        }
        
        function updateCartSummary() {
            const cart = JSON.parse(localStorage.getItem('cart')) || [];
            const subtotal = cart.reduce((sum, item) => sum + (item.price * item.quantity), 0);
            const shipping = subtotal > 1000000 ? 0 : 50000; // Free shipping over 1M
            const discount = 0; // Will be calculated when coupon is applied
            const total = subtotal + shipping - discount;
            
            document.getElementById('subtotal').textContent = formatPrice(subtotal);
            document.getElementById('shipping').textContent = shipping === 0 ? 'Miễn phí' : formatPrice(shipping);
            document.getElementById('discount').textContent = discount === 0 ? '0₫' : `-${formatPrice(discount)}`;
            document.getElementById('total').textContent = formatPrice(total);
        }
        
        function applyCoupon() {
            const couponCode = document.getElementById('couponCode').value.trim();
            if (!couponCode) {
                showNotification('Vui lòng nhập mã giảm giá!', 'warning');
                return;
            }
            
            // Mock coupon validation
            const validCoupons = {
                'WELCOME10': 0.1,
                'SAVE20': 0.2,
                'NEWUSER': 0.15
            };
            
            if (validCoupons[couponCode]) {
                const cart = JSON.parse(localStorage.getItem('cart')) || [];
                const subtotal = cart.reduce((sum, item) => sum + (item.price * item.quantity), 0);
                const discount = subtotal * validCoupons[couponCode];
                
                document.getElementById('discount').textContent = `-${formatPrice(discount)}`;
                updateCartSummary();
                showNotification('Áp dụng mã giảm giá thành công!', 'success');
            } else {
                showNotification('Mã giảm giá không hợp lệ!', 'error');
            }
        }
        
        function checkout() {
            const cart = JSON.parse(localStorage.getItem('cart')) || [];
            if (cart.length === 0) {
                showNotification('Giỏ hàng trống!', 'warning');
                return;
            }
            
            // Check if user is logged in
            const token = localStorage.getItem('token');
            if (!token) {
                showNotification('Vui lòng đăng nhập để thanh toán!', 'warning');
                setTimeout(() => {
                    window.location.href = '${pageContext.request.contextPath}/auth/login';
                }, 2000);
                return;
            }
            
            // Proceed to checkout
            showNotification('Chuyển đến trang thanh toán...', 'info');
            setTimeout(() => {
                window.location.href = '${pageContext.request.contextPath}/checkout';
            }, 1000);
        }
        
        function formatPrice(price) {
            return new Intl.NumberFormat('vi-VN', {
                style: 'currency',
                currency: 'VND'
            }).format(price);
        }
        
        function updateCartBadge() {
            const cart = JSON.parse(localStorage.getItem('cart')) || [];
            const totalItems = cart.reduce((sum, item) => sum + item.quantity, 0);
            const badge = document.querySelector('.cart-badge');
            if (badge) {
                badge.textContent = totalItems;
                badge.style.display = totalItems > 0 ? 'flex' : 'none';
            }
        }
    </script>
</body>
</html>
