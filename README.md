# 📚 **UTE Phone Hub - Documentation**

## 🎯 **Tổng quan**

Đây là tài liệu đầy đủ cho dự án **UTE Phone Hub** - hệ thống bán điện thoại trực tuyến được xây dựng bằng **Java Servlet/JSP** với kiến trúc MVC truyền thống.

### **🏗️ Tech Stack**
- **Backend**: Java 17, Servlet API 6.1.0, JSP 2.3+, JSTL 1.2+
- **Database**: PostgreSQL 13+, JPA/Hibernate 5.6+
- **Frontend**: HTML5, CSS3, JavaScript ES6+, Bootstrap 5
- **Build**: Maven 3.8+, Tomcat 9.0+
- **Deployment**: Docker + Docker Compose
- **UI Reference**: [Thegioididong.com](https://www.thegioididong.com/)

---

## 📁 **Cấu trúc Documentation**

### **⚡ [Critical Rules](./critical-rules.md)** - **🚨 ĐỌC TRƯỚC KHI BẮT ĐẦU**
- **Security Rules** - Quy tắc bảo mật bắt buộc
- **Architecture Rules** - Quy tắc kiến trúc
- **Coding Standards** - Chuẩn coding
- **Database Rules** - Quy tắc database
- **Frontend Rules** - Quy tắc frontend
- **Deployment Rules** - Quy tắc deployment

### **🏗️ [Architecture](./architecture/)**
- **[Overview](./architecture/overview.md)** - Tổng quan kiến trúc hệ thống
- **[Class Diagram](./architecture/class-diagram.md)** - Sơ đồ lớp và mối quan hệ
- **[Patterns](./architecture/patterns.md)** - Design patterns và best practices

### **🔌 [API](./api/)**
- **[Endpoints](./api/endpoints.md)** - Tài liệu API đầy đủ
- **[Data Forms](./api/data-forms.md)** - Cấu trúc request/response DTOs
- **[Postman Collection](./api/postman-collection.json)** - Collection để test API
- **[Postman Environment](./api/postman-environment.json)** - Environment variables
- **[Postman Guide](./api/postman-guide.md)** - Hướng dẫn sử dụng Postman

### **🗄️ [Database](./database/)**
- **[Schema](./database/schema.md)** - Thiết kế database và bảng
- **[Relationships](./database/relationships.md)** - Mối quan hệ giữa các bảng

### **💻 [Development](./development/)**
- **[Coding Standards](./development/coding-standards.md)** - Quy tắc coding và naming
- **[Folder Structure](./development/folder-structure.md)** - Cấu trúc thư mục dự án
- **[Memory Bank](./development/memory-bank.md)** - Code snippets và patterns

### **🚀 [Deployment](./deployment/)**
- **[Docker Setup](./deployment/docker-setup.md)** - Cấu hình Docker development
- **[Production](./deployment/production.md)** - Deploy production với Docker

---

## ⚡ **Quick Start**

### **🔧 Development Setup**
```bash
# Clone repository
git clone <repository-url>
cd ute-phone-hub

# Start với Docker
docker-compose up -d

# Access application
http://localhost:8080
```

### **📋 Prerequisites**
- Java 17+
- Maven 3.8+
- Docker & Docker Compose
- PostgreSQL 13+ (hoặc dùng Docker)

---

## 🎯 **Tính năng chính**

### **👥 User Features**
- ✅ Đăng ký/Đăng nhập với JWT
- ✅ Duyệt sản phẩm theo danh mục/thương hiệu
- ✅ Tìm kiếm và lọc sản phẩm
- ✅ Giỏ hàng và checkout
- ✅ Quản lý đơn hàng
- ✅ Đánh giá sản phẩm
- ✅ Guest checkout (không cần đăng ký)

### **👨‍💼 Admin Features**
- ✅ Dashboard thống kê
- ✅ Quản lý sản phẩm (CRUD)
- ✅ Quản lý đơn hàng
- ✅ Quản lý người dùng
- ✅ Quản lý mã giảm giá
- ✅ Quản lý danh mục/thương hiệu

---

## 🔒 **Security Features**

- **JWT Authentication** với refresh token
- **Password Hashing** bằng BCrypt
- **Input Validation** server-side
- **SQL Injection Prevention** với JPA
- **XSS Prevention** với JSTL escaping
- **Rate Limiting** cho API endpoints

---

## 📊 **Database Schema**

Hệ thống sử dụng **14 bảng chính**:
- `users`, `addresses` - Quản lý người dùng
- `products`, `categories`, `brands` - Quản lý sản phẩm
- `orders`, `order_items` - Quản lý đơn hàng
- `carts`, `cart_items` - Giỏ hàng
- `reviews`, `review_likes` - Đánh giá
- `vouchers` - Mã giảm giá
- `password_reset_tokens` - Reset mật khẩu

---

## 🎨 **UI/UX Design**

Giao diện được thiết kế theo phong cách **Thegioididong.com**:
- **Responsive Design** - Mobile-first approach
- **Modern UI** - Clean, professional layout
- **User-friendly** - Intuitive navigation
- **Performance** - Fast loading, optimized images

---

## 📞 **Support & Contact**

### **Development Team**
- **Lead Developer**: [Name] - [Email]
- **Backend Developer**: [Name] - [Email]
- **Frontend Developer**: [Name] - [Email]

### **Documentation**
- **Last Updated**: January 15, 2025
- **Version**: 1.0
- **Maintained by**: UTE-PhoneHub Development Team

---

## 🔗 **Useful Links**

- [Java Servlet Documentation](https://jakarta.ee/specifications/servlet/)
- [JSP Documentation](https://jakarta.ee/specifications/pages/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Docker Documentation](https://docs.docker.com/)
- [Bootstrap Documentation](https://getbootstrap.com/docs/)

---

**📝 Note**: Tài liệu này được cập nhật thường xuyên. Vui lòng kiểm tra phiên bản mới nhất trước khi bắt đầu development.
