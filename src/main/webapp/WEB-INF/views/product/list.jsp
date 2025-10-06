<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sản phẩm - UTE Phone Hub</title>
    
    <!-- Favicon -->
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/static/favicon.png">
    <link rel="shortcut icon" type="image/png" href="${pageContext.request.contextPath}/static/favicon.png">
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/components/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/components/product.css">
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
                        <input type="text" class="search-input" placeholder="Tìm kiếm sản phẩm..." value="${param.q}">
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
                    <a href="${pageContext.request.contextPath}/cart" class="header-action">
                        <i class="fas fa-shopping-cart"></i>
                        <span>Giỏ hàng</span>
                        <span class="cart-badge" style="display: none;">0</span>
                    </a>
                </div>
            </div>
        </div>
        
        <!-- Navigation -->
        <nav class="nav">
            <div class="nav-container">
                <ul class="nav-list">
                    <li class="nav-item">
                        <a href="${pageContext.request.contextPath}/" class="nav-link">
                            <i class="fas fa-home"></i>
                            <span>Trang chủ</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="${pageContext.request.contextPath}/products?category=phone" class="nav-link ${param.category == 'phone' ? 'active' : ''}">
                            <i class="fas fa-mobile-alt"></i>
                            <span>Điện thoại</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="${pageContext.request.contextPath}/products?category=laptop" class="nav-link ${param.category == 'laptop' ? 'active' : ''}">
                            <i class="fas fa-laptop"></i>
                            <span>Laptop</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="${pageContext.request.contextPath}/products?category=accessories" class="nav-link ${param.category == 'accessories' ? 'active' : ''}">
                            <i class="fas fa-headphones"></i>
                            <span>Phụ kiện</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="${pageContext.request.contextPath}/products?category=smartwatch" class="nav-link ${param.category == 'smartwatch' ? 'active' : ''}">
                            <i class="fas fa-clock"></i>
                            <span>Smartwatch</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="${pageContext.request.contextPath}/products?category=tablet" class="nav-link ${param.category == 'tablet' ? 'active' : ''}">
                            <i class="fas fa-tablet-alt"></i>
                            <span>Tablet</span>
                        </a>
                    </li>
                </ul>
            </div>
        </nav>
    </header>

    <!-- Main Content -->
    <main class="main-content">
        <!-- Breadcrumb -->
        <div class="breadcrumb">
            <a href="${pageContext.request.contextPath}/">Trang chủ</a>
            <i class="fas fa-chevron-right"></i>
            <span>Sản phẩm</span>
            <c:if test="${not empty param.category}">
                <i class="fas fa-chevron-right"></i>
                <span>${param.category}</span>
            </c:if>
        </div>

        <!-- Filters -->
        <div class="product-filters">
            <div class="filter-group">
                <h3 class="filter-title">Danh mục</h3>
                <div class="filter-options">
                    <div class="filter-option ${param.category == 'phone' ? 'active' : ''}" data-filter="category" data-value="phone">
                        <i class="fas fa-mobile-alt"></i>
                        Điện thoại
                    </div>
                    <div class="filter-option ${param.category == 'laptop' ? 'active' : ''}" data-filter="category" data-value="laptop">
                        <i class="fas fa-laptop"></i>
                        Laptop
                    </div>
                    <div class="filter-option ${param.category == 'accessories' ? 'active' : ''}" data-filter="category" data-value="accessories">
                        <i class="fas fa-headphones"></i>
                        Phụ kiện
                    </div>
                    <div class="filter-option ${param.category == 'smartwatch' ? 'active' : ''}" data-filter="category" data-value="smartwatch">
                        <i class="fas fa-clock"></i>
                        Smartwatch
                    </div>
                    <div class="filter-option ${param.category == 'tablet' ? 'active' : ''}" data-filter="category" data-value="tablet">
                        <i class="fas fa-tablet-alt"></i>
                        Tablet
                    </div>
                </div>
            </div>
            
            <div class="filter-group">
                <h3 class="filter-title">Giá</h3>
                <div class="filter-options">
                    <div class="filter-option" data-filter="price" data-value="0-5000000">Dưới 5 triệu</div>
                    <div class="filter-option" data-filter="price" data-value="5000000-10000000">5 - 10 triệu</div>
                    <div class="filter-option" data-filter="price" data-value="10000000-20000000">10 - 20 triệu</div>
                    <div class="filter-option" data-filter="price" data-value="20000000-999999999">Trên 20 triệu</div>
                </div>
            </div>
            
            <div class="filter-group">
                <h3 class="filter-title">Thương hiệu</h3>
                <div class="filter-options">
                    <div class="filter-option" data-filter="brand" data-value="apple">Apple</div>
                    <div class="filter-option" data-filter="brand" data-value="samsung">Samsung</div>
                    <div class="filter-option" data-filter="brand" data-value="xiaomi">Xiaomi</div>
                    <div class="filter-option" data-filter="brand" data-value="oppo">OPPO</div>
                    <div class="filter-option" data-filter="brand" data-value="vivo">vivo</div>
                    <div class="filter-option" data-filter="brand" data-value="realme">realme</div>
                </div>
            </div>
            
            <div class="filter-group">
                <h3 class="filter-title">Sắp xếp</h3>
                <div class="filter-options">
                    <div class="filter-option active" data-filter="sort" data-value="newest">Mới nhất</div>
                    <div class="filter-option" data-filter="sort" data-value="price-asc">Giá thấp đến cao</div>
                    <div class="filter-option" data-filter="sort" data-value="price-desc">Giá cao đến thấp</div>
                    <div class="filter-option" data-filter="sort" data-value="rating">Đánh giá cao</div>
                    <div class="filter-option" data-filter="sort" data-value="popular">Bán chạy</div>
                </div>
            </div>
        </div>

        <!-- Products Grid -->
        <div class="products-section">
            <div class="section-header">
                <h2>Sản phẩm</h2>
                <div class="view-options">
                    <button class="view-btn active" data-view="grid">
                        <i class="fas fa-th"></i>
                    </button>
                    <button class="view-btn" data-view="list">
                        <i class="fas fa-list"></i>
                    </button>
                </div>
            </div>
            
            <div class="product-grid" id="productGrid">
                <!-- Sample Products -->
                <div class="product-card" data-product-id="iphone-15-pro-max">
                    <div class="product-image-container">
                        <img src="https://via.placeholder.com/300x200/ff6b35/ffffff?text=iPhone+15+Pro+Max" alt="iPhone 15 Pro Max" class="product-image">
                        <div class="product-badges">
                            <span class="badge badge-new">Mới</span>
                            <span class="badge badge-ai">AI</span>
                        </div>
                        <button class="product-wishlist">
                            <i class="fas fa-heart"></i>
                        </button>
                    </div>
                    <div class="product-info">
                        <div class="product-category">Điện thoại</div>
                        <h3 class="product-title">iPhone 15 Pro Max 256GB</h3>
                        <div class="product-rating">
                            <div class="rating-stars">
                                <i class="fas fa-star star"></i>
                                <i class="fas fa-star star"></i>
                                <i class="fas fa-star star"></i>
                                <i class="fas fa-star star"></i>
                                <i class="fas fa-star star"></i>
                            </div>
                            <span class="rating-text">(4.9)</span>
                        </div>
                        <div class="product-price">
                            <span class="price-current">32.990.000₫</span>
                            <span class="price-original">34.990.000₫</span>
                            <span class="discount-percent">-5%</span>
                        </div>
                        <div class="product-stock">
                            <div class="stock-bar">
                                <div class="stock-progress" style="width: 80%;"></div>
                            </div>
                            <div class="stock-text">Còn 8/10 suất</div>
                        </div>
                        <div class="product-actions">
                            <button class="btn-add-cart">Mua ngay</button>
                            <button class="btn-quick-view">
                                <i class="fas fa-eye"></i>
                            </button>
                        </div>
                    </div>
                </div>

                <div class="product-card" data-product-id="samsung-galaxy-s24-ultra">
                    <div class="product-image-container">
                        <img src="https://via.placeholder.com/300x200/ff6b35/ffffff?text=Samsung+Galaxy+S24+Ultra" alt="Samsung Galaxy S24 Ultra" class="product-image">
                        <div class="product-badges">
                            <span class="badge badge-sale">-8%</span>
                        </div>
                        <button class="product-wishlist">
                            <i class="fas fa-heart"></i>
                        </button>
                    </div>
                    <div class="product-info">
                        <div class="product-category">Điện thoại</div>
                        <h3 class="product-title">Samsung Galaxy S24 Ultra 512GB</h3>
                        <div class="product-rating">
                            <div class="rating-stars">
                                <i class="fas fa-star star"></i>
                                <i class="fas fa-star star"></i>
                                <i class="fas fa-star star"></i>
                                <i class="fas fa-star star"></i>
                                <i class="fas fa-star star"></i>
                            </div>
                            <span class="rating-text">(4.8)</span>
                        </div>
                        <div class="product-price">
                            <span class="price-current">28.990.000₫</span>
                            <span class="price-original">31.490.000₫</span>
                            <span class="discount-percent">-8%</span>
                        </div>
                        <div class="product-stock">
                            <div class="stock-bar">
                                <div class="stock-progress" style="width: 100%;"></div>
                            </div>
                            <div class="stock-text">Còn 10/10 suất</div>
                        </div>
                        <div class="product-actions">
                            <button class="btn-add-cart">Mua ngay</button>
                            <button class="btn-quick-view">
                                <i class="fas fa-eye"></i>
                            </button>
                        </div>
                    </div>
                </div>

                <div class="product-card" data-product-id="macbook-pro-m3">
                    <div class="product-image-container">
                        <img src="https://via.placeholder.com/300x200/ff6b35/ffffff?text=MacBook+Pro+M3" alt="MacBook Pro M3" class="product-image">
                        <div class="product-badges">
                            <span class="badge badge-new">Mới</span>
                        </div>
                        <button class="product-wishlist">
                            <i class="fas fa-heart"></i>
                        </button>
                    </div>
                    <div class="product-info">
                        <div class="product-category">Laptop</div>
                        <h3 class="product-title">MacBook Pro 14" M3 512GB</h3>
                        <div class="product-rating">
                            <div class="rating-stars">
                                <i class="fas fa-star star"></i>
                                <i class="fas fa-star star"></i>
                                <i class="fas fa-star star"></i>
                                <i class="fas fa-star star"></i>
                                <i class="fas fa-star star"></i>
                            </div>
                            <span class="rating-text">(4.9)</span>
                        </div>
                        <div class="product-price">
                            <span class="price-current">45.990.000₫</span>
                            <span class="price-original">47.990.000₫</span>
                            <span class="discount-percent">-4%</span>
                        </div>
                        <div class="product-stock">
                            <div class="stock-bar">
                                <div class="stock-progress" style="width: 60%;"></div>
                            </div>
                            <div class="stock-text">Còn 6/10 suất</div>
                        </div>
                        <div class="product-actions">
                            <button class="btn-add-cart">Mua ngay</button>
                            <button class="btn-quick-view">
                                <i class="fas fa-eye"></i>
                            </button>
                        </div>
                    </div>
                </div>

                <div class="product-card" data-product-id="airpods-pro-2">
                    <div class="product-image-container">
                        <img src="https://via.placeholder.com/300x200/ff6b35/ffffff?text=AirPods+Pro+2" alt="AirPods Pro 2" class="product-image">
                        <div class="product-badges">
                            <span class="badge badge-sale">-10%</span>
                        </div>
                        <button class="product-wishlist">
                            <i class="fas fa-heart"></i>
                        </button>
                    </div>
                    <div class="product-info">
                        <div class="product-category">Phụ kiện</div>
                        <h3 class="product-title">AirPods Pro 2 (USB-C)</h3>
                        <div class="product-rating">
                            <div class="rating-stars">
                                <i class="fas fa-star star"></i>
                                <i class="fas fa-star star"></i>
                                <i class="fas fa-star star"></i>
                                <i class="fas fa-star star"></i>
                                <i class="fas fa-star star"></i>
                            </div>
                            <span class="rating-text">(4.7)</span>
                        </div>
                        <div class="product-price">
                            <span class="price-current">5.490.000₫</span>
                            <span class="price-original">6.090.000₫</span>
                            <span class="discount-percent">-10%</span>
                        </div>
                        <div class="product-stock">
                            <div class="stock-bar">
                                <div class="stock-progress" style="width: 90%;"></div>
                            </div>
                            <div class="stock-text">Còn 9/10 suất</div>
                        </div>
                        <div class="product-actions">
                            <button class="btn-add-cart">Mua ngay</button>
                            <button class="btn-quick-view">
                                <i class="fas fa-eye"></i>
                            </button>
                        </div>
                    </div>
                </div>

                <div class="product-card" data-product-id="apple-watch-series-9">
                    <div class="product-image-container">
                        <img src="https://via.placeholder.com/300x200/ff6b35/ffffff?text=Apple+Watch+Series+9" alt="Apple Watch Series 9" class="product-image">
                        <div class="product-badges">
                            <span class="badge badge-sale">-12%</span>
                        </div>
                        <button class="product-wishlist">
                            <i class="fas fa-heart"></i>
                        </button>
                    </div>
                    <div class="product-info">
                        <div class="product-category">Smartwatch</div>
                        <h3 class="product-title">Apple Watch Series 9 45mm GPS</h3>
                        <div class="product-rating">
                            <div class="rating-stars">
                                <i class="fas fa-star star"></i>
                                <i class="fas fa-star star"></i>
                                <i class="fas fa-star star"></i>
                                <i class="fas fa-star star"></i>
                                <i class="fas fa-star star"></i>
                            </div>
                            <span class="rating-text">(4.6)</span>
                        </div>
                        <div class="product-price">
                            <span class="price-current">8.990.000₫</span>
                            <span class="price-original">10.190.000₫</span>
                            <span class="discount-percent">-12%</span>
                        </div>
                        <div class="product-stock">
                            <div class="stock-bar">
                                <div class="stock-progress" style="width: 70%;"></div>
                            </div>
                            <div class="stock-text">Còn 7/10 suất</div>
                        </div>
                        <div class="product-actions">
                            <button class="btn-add-cart">Mua ngay</button>
                            <button class="btn-quick-view">
                                <i class="fas fa-eye"></i>
                            </button>
                        </div>
                    </div>
                </div>

                <div class="product-card" data-product-id="ipad-air-m2">
                    <div class="product-image-container">
                        <img src="https://via.placeholder.com/300x200/ff6b35/ffffff?text=iPad+Air+M2" alt="iPad Air M2" class="product-image">
                        <div class="product-badges">
                            <span class="badge badge-new">Mới</span>
                        </div>
                        <button class="product-wishlist">
                            <i class="fas fa-heart"></i>
                        </button>
                    </div>
                    <div class="product-info">
                        <div class="product-category">Tablet</div>
                        <h3 class="product-title">iPad Air 11" M2 256GB</h3>
                        <div class="product-rating">
                            <div class="rating-stars">
                                <i class="fas fa-star star"></i>
                                <i class="fas fa-star star"></i>
                                <i class="fas fa-star star"></i>
                                <i class="fas fa-star star"></i>
                                <i class="fas fa-star star"></i>
                            </div>
                            <span class="rating-text">(4.8)</span>
                        </div>
                        <div class="product-price">
                            <span class="price-current">18.990.000₫</span>
                            <span class="price-original">19.990.000₫</span>
                            <span class="discount-percent">-5%</span>
                        </div>
                        <div class="product-stock">
                            <div class="stock-bar">
                                <div class="stock-progress" style="width: 100%;"></div>
                            </div>
                            <div class="stock-text">Còn 10/10 suất</div>
                        </div>
                        <div class="product-actions">
                            <button class="btn-add-cart">Mua ngay</button>
                            <button class="btn-quick-view">
                                <i class="fas fa-eye"></i>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Pagination -->
        <div class="product-pagination">
            <button class="pagination-btn" disabled>
                <i class="fas fa-chevron-left"></i>
            </button>
            <button class="pagination-btn active">1</button>
            <button class="pagination-btn">2</button>
            <button class="pagination-btn">3</button>
            <button class="pagination-btn">4</button>
            <button class="pagination-btn">5</button>
            <button class="pagination-btn">
                <i class="fas fa-chevron-right"></i>
            </button>
        </div>
    </main>

    <style>
        .breadcrumb {
            display: flex;
            align-items: center;
            gap: 10px;
            margin-bottom: 20px;
            font-size: 14px;
            color: #666;
        }
        
        .breadcrumb a {
            color: #ff6b35;
            text-decoration: none;
        }
        
        .breadcrumb a:hover {
            text-decoration: underline;
        }
        
        .breadcrumb i {
            font-size: 12px;
        }
        
        .view-options {
            display: flex;
            gap: 5px;
        }
        
        .view-btn {
            padding: 10px 15px;
            border: 2px solid #e0e0e0;
            background: white;
            color: #666;
            border-radius: 8px;
            cursor: pointer;
            transition: all 0.3s;
        }
        
        .view-btn.active {
            background: #ff6b35;
            border-color: #ff6b35;
            color: white;
        }
        
        .view-btn:hover {
            border-color: #ff6b35;
            color: #ff6b35;
        }
        
        .product-list {
            display: none;
        }
        
        .product-list.active {
            display: block;
        }
        
        .product-grid.active {
            display: grid;
        }
        
        .product-grid:not(.active) {
            display: none;
        }
    </style>

    <script>
        // View toggle
        document.querySelectorAll('.view-btn').forEach(btn => {
            btn.addEventListener('click', function() {
                document.querySelectorAll('.view-btn').forEach(b => b.classList.remove('active'));
                this.classList.add('active');
                
                const view = this.dataset.view;
                const grid = document.getElementById('productGrid');
                
                if (view === 'list') {
                    grid.classList.remove('product-grid');
                    grid.classList.add('product-list');
                } else {
                    grid.classList.remove('product-list');
                    grid.classList.add('product-grid');
                }
            });
        });
        
        // Filter functionality
        document.querySelectorAll('.filter-option').forEach(option => {
            option.addEventListener('click', function() {
                const filterType = this.dataset.filter;
                const filterValue = this.dataset.value;
                
                if (filterType === 'sort') {
                    // Remove active from all sort options
                    document.querySelectorAll('[data-filter="sort"]').forEach(opt => {
                        opt.classList.remove('active');
                    });
                    this.classList.add('active');
                    
                    // Apply sorting
                    sortProducts(filterValue);
                } else {
                    // Toggle filter
                    this.classList.toggle('active');
                    applyFilters();
                }
            });
        });
        
        function applyFilters() {
            const activeFilters = {};
            
            document.querySelectorAll('.filter-option.active').forEach(option => {
                const filterType = option.dataset.filter;
                const filterValue = option.dataset.value;
                
                if (filterType !== 'sort') {
                    if (!activeFilters[filterType]) {
                        activeFilters[filterType] = [];
                    }
                    activeFilters[filterType].push(filterValue);
                }
            });
            
            console.log('Active filters:', activeFilters);
            // Implement actual filtering logic here
        }
        
        function sortProducts(sortBy) {
            const productGrid = document.getElementById('productGrid');
            const products = Array.from(productGrid.children);
            
            products.sort((a, b) => {
                switch(sortBy) {
                    case 'price-asc':
                        return getPrice(a) - getPrice(b);
                    case 'price-desc':
                        return getPrice(b) - getPrice(a);
                    case 'rating':
                        return getRating(b) - getRating(a);
                    case 'newest':
                    default:
                        return 0; // Keep original order
                }
            });
            
            products.forEach(product => productGrid.appendChild(product));
        }
        
        function getPrice(product) {
            const priceText = product.querySelector('.price-current').textContent;
            return parseInt(priceText.replace(/[^\d]/g, ''));
        }
        
        function getRating(product) {
            const ratingText = product.querySelector('.rating-text').textContent;
            return parseFloat(ratingText.match(/\(([\d.]+)\)/)[1]);
        }
    </script>
</body>
</html>
