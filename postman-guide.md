# 📮 **Postman Collection Guide**

## 📋 **Tổng quan**

Hướng dẫn sử dụng Postman Collection để test API UTE Phone Hub một cách hiệu quả.

---

## 🚀 **Quick Start**

### **1. Import Collection & Environment**
1. **Import Collection**: File `postman-collection.json`
2. **Import Environment**: File `postman-environment.json`
3. **Select Environment**: Chọn "UTE Phone Hub Environment"

### **2. Setup Development Server**
```bash
# Start development server
docker-compose up -d

# Verify server is running
curl http://localhost:8080/health
```

### **3. Test Authentication**
1. Chạy **"Register User"** hoặc **"Login User"**
2. Access token sẽ được tự động lưu vào environment
3. Tất cả requests sau sẽ tự động sử dụng token

---

## 📁 **Collection Structure**

### **🔐 Authentication**
- **Register User** - Tạo tài khoản mới
- **Login User** - Đăng nhập và lấy token

### **👤 User Management**
- **Get Current User** - Lấy thông tin user hiện tại
- **Update User Profile** - Cập nhật thông tin cá nhân
- **Change Password** - Đổi mật khẩu
- **Get User Addresses** - Lấy danh sách địa chỉ
- **Create Address** - Tạo địa chỉ mới

### **📱 Categories & Brands**
- **Get All Categories** - Lấy danh sách danh mục
- **Get All Brands** - Lấy danh sách thương hiệu

### **🛍️ Products**
- **Get Products List** - Lấy danh sách sản phẩm
- **Get Products with Filters** - Lọc sản phẩm theo điều kiện
- **Get Product Detail** - Chi tiết sản phẩm
- **Get Product Reviews** - Đánh giá sản phẩm

### **🛒 Shopping Cart**
- **Get Cart** - Lấy giỏ hàng
- **Add Item to Cart** - Thêm sản phẩm vào giỏ
- **Update Cart Item** - Cập nhật số lượng
- **Remove Cart Item** - Xóa sản phẩm khỏi giỏ

### **📦 Orders**
- **Create Order (Checkout)** - Tạo đơn hàng
- **Get User Orders** - Lấy danh sách đơn hàng
- **Get Order Detail** - Chi tiết đơn hàng
- **Order Lookup (Public)** - Tra cứu đơn hàng (không cần đăng nhập)

### **⭐ Reviews**
- **Create Review** - Tạo đánh giá
- **Like Review** - Thích đánh giá
- **Unlike Review** - Bỏ thích đánh giá

### **👨‍💼 Admin**
- **Admin Dashboard Summary** - Tổng quan dashboard
- **Admin - Get Products** - Quản lý sản phẩm
- **Admin - Create Product** - Tạo sản phẩm mới
- **Admin - Update Product** - Cập nhật sản phẩm
- **Admin - Delete Product** - Xóa sản phẩm
- **Admin - Get Orders** - Quản lý đơn hàng
- **Admin - Update Order Status** - Cập nhật trạng thái đơn hàng
- **Admin - Get Users** - Quản lý người dùng
- **Admin - Update User Status** - Cập nhật trạng thái user
- **Admin - Get Vouchers** - Quản lý mã giảm giá
- **Admin - Create Voucher** - Tạo mã giảm giá mới

---

## 🔧 **Environment Variables**

### **🌐 Server Configuration**
- **`baseUrl`**: `http://localhost:8080/api/v1` - Base URL của API
- **`accessToken`**: JWT token (tự động cập nhật khi login/register)
- **`userId`**: ID của user hiện tại (tự động cập nhật)

### **🛍️ Testing Data**
- **`productId`**: `1` - ID sản phẩm mẫu để test
- **`orderId`**: ID đơn hàng (tự động cập nhật khi tạo order)
- **`categoryId`**: `1` - ID danh mục mẫu
- **`brandId`**: `1` - ID thương hiệu mẫu
- **`voucherCode`**: `SALE50K` - Mã giảm giá mẫu

### **👤 Test Accounts**
- **`testEmail`**: `test@example.com` - Email test
- **`testPassword`**: `Password123!` - Password test
- **`adminEmail`**: `admin@utephonehub.com` - Admin email
- **`adminPassword`**: `AdminPassword123!` - Admin password

---

## 🧪 **Testing Workflows**

### **🔄 Complete User Journey**
```
1. Register User → Get accessToken
2. Get Current User → Verify user info
3. Create Address → Add shipping address
4. Get All Categories → Browse categories
5. Get Products List → Browse products
6. Get Product Detail → View product details
7. Add Item to Cart → Add to cart
8. Get Cart → Verify cart contents
9. Create Order (Checkout) → Complete purchase
10. Get User Orders → View order history
```

### **👨‍💼 Admin Workflow**
```
1. Login as Admin → Get admin token
2. Admin Dashboard Summary → View overview
3. Admin - Get Products → Manage products
4. Admin - Create Product → Add new product
5. Admin - Get Orders → Manage orders
6. Admin - Update Order Status → Process orders
7. Admin - Get Users → Manage users
8. Admin - Create Voucher → Add discount codes
```

### **🛒 Shopping Cart Testing**
```
1. Login User → Authenticate
2. Add Item to Cart → Add product
3. Update Cart Item → Change quantity
4. Get Cart → Verify cart
5. Remove Cart Item → Remove product
6. Get Cart → Verify empty cart
```

---

## 🔍 **Test Scripts**

### **📊 Global Test Scripts**
Collection có sẵn các test scripts tự động:

```javascript
// Response time test
pm.test('Response time is less than 5000ms', function () {
    pm.expect(pm.response.responseTime).to.be.below(5000);
});

// Success field test
pm.test('Response has success field', function () {
    const response = pm.response.json();
    pm.expect(response).to.have.property('success');
});
```

### **🔐 Authentication Test Scripts**
```javascript
// Auto-save access token
if (pm.response.code === 200) {
    const response = pm.response.json();
    if (response.success && response.data.accessToken) {
        pm.collectionVariables.set('accessToken', response.data.accessToken);
        pm.collectionVariables.set('userId', response.data.user.id);
        console.log('Access token saved:', response.data.accessToken);
    }
}
```

### **📦 Order Test Scripts**
```javascript
// Auto-save order ID
if (pm.response.code === 201) {
    const response = pm.response.json();
    if (response.success && response.data.orderId) {
        pm.collectionVariables.set('orderId', response.data.orderId);
        console.log('Order ID saved:', response.data.orderId);
    }
}
```

---

## 🚨 **Common Issues & Solutions**

### **❌ Authentication Issues**
**Problem**: 401 Unauthorized
**Solution**: 
1. Chạy "Login User" trước
2. Kiểm tra `accessToken` trong environment
3. Verify token chưa hết hạn

### **❌ Server Connection Issues**
**Problem**: Connection refused
**Solution**:
1. Kiểm tra server đang chạy: `docker-compose ps`
2. Verify `baseUrl` trong environment
3. Check firewall settings

### **❌ Data Not Found**
**Problem**: 404 Not Found
**Solution**:
1. Kiểm tra `productId`, `orderId` trong environment
2. Verify data tồn tại trong database
3. Check API endpoint URL

### **❌ Validation Errors**
**Problem**: 400 Bad Request
**Solution**:
1. Kiểm tra request body format
2. Verify required fields
3. Check data types và constraints

---

## 📈 **Performance Testing**

### **⚡ Load Testing với Postman**
1. **Collection Runner**: Chạy nhiều requests cùng lúc
2. **Monitor Response Times**: Theo dõi thời gian phản hồi
3. **Test Concurrent Users**: Test với nhiều user đồng thời

### **📊 Metrics to Monitor**
- **Response Time**: < 5000ms
- **Success Rate**: > 95%
- **Error Rate**: < 5%
- **Throughput**: Requests per second

---

## 🔧 **Advanced Usage**

### **🔄 Automated Testing**
```javascript
// Test data validation
pm.test('Product has required fields', function () {
    const product = pm.response.json().data;
    pm.expect(product).to.have.property('id');
    pm.expect(product).to.have.property('name');
    pm.expect(product).to.have.property('price');
});

// Test business logic
pm.test('Cart total calculation', function () {
    const cart = pm.response.json().data;
    let calculatedTotal = 0;
    cart.items.forEach(item => {
        calculatedTotal += item.price * item.quantity;
    });
    pm.expect(cart.totalPrice).to.equal(calculatedTotal.toString());
});
```

### **📝 Data-Driven Testing**
1. Tạo CSV file với test data
2. Import vào Postman
3. Sử dụng `{{variable}}` trong requests
4. Chạy Collection Runner với data file

### **🔗 API Chaining**
```javascript
// Chain multiple API calls
pm.test('Complete order flow', function () {
    // Step 1: Add to cart
    pm.sendRequest({
        url: pm.collectionVariables.get('baseUrl') + '/cart/items',
        method: 'POST',
        header: {
            'Authorization': 'Bearer ' + pm.collectionVariables.get('accessToken'),
            'Content-Type': 'application/json'
        },
        body: {
            mode: 'raw',
            raw: JSON.stringify({
                productId: pm.collectionVariables.get('productId'),
                quantity: 1
            })
        }
    }, function (err, response) {
        // Step 2: Create order
        pm.sendRequest({
            url: pm.collectionVariables.get('baseUrl') + '/checkout',
            method: 'POST',
            // ... order creation logic
        });
    });
});
```

---

## 📚 **Best Practices**

### **✅ Do's**
- **Always test authentication first** - Đảm bảo token hợp lệ
- **Use environment variables** - Không hardcode values
- **Test error scenarios** - Test cả success và error cases
- **Verify response structure** - Kiểm tra format response
- **Clean up test data** - Xóa data test sau khi hoàn thành

### **❌ Don'ts**
- **Don't hardcode sensitive data** - Không hardcode passwords
- **Don't skip validation tests** - Luôn test validation
- **Don't ignore error responses** - Test cả error cases
- **Don't leave test data** - Clean up sau khi test

---

## 🆘 **Support & Troubleshooting**

### **📞 Getting Help**
- **Documentation Issues**: Check `docs/api/endpoints.md`
- **Technical Issues**: Check `docs/critical-rules.md`
- **Setup Issues**: Check `docs/deployment/docker-setup.md`

### **🔍 Debug Tips**
- **Check Console**: View Postman console for logs
- **Verify Environment**: Ensure correct environment is selected
- **Test Individual Requests**: Test từng request riêng lẻ
- **Check Server Logs**: Monitor server logs for errors

---

**📝 Last Updated**: January 15, 2025  
**📝 Version**: 1.0  
**👥 Maintained by**: UTE-PhoneHub Development Team
