# 🚀 **UTE Phone Hub - Setup Guide**

## 📋 **Tổng quan**

Hướng dẫn setup và build dự án UTE Phone Hub với PostgreSQL database.

---

## 🔧 **Prerequisites**

### **Required Software**
- **Java 17+** - [Download Oracle JDK](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) hoặc [OpenJDK](https://adoptium.net/)
- **Maven 3.8+** - [Download Maven](https://maven.apache.org/download.cgi)
- **Docker & Docker Compose** - [Download Docker Desktop](https://www.docker.com/products/docker-desktop/)
- **PostgreSQL 13+** (optional - có thể dùng Docker)

### **IDE Recommendations**
- **IntelliJ IDEA** - Recommended
- **Eclipse** - With Jakarta EE support
- **VS Code** - With Java extensions

---

## 🚀 **Quick Start**

### **1. Clone Repository**
```bash
git clone <repository-url>
cd ute-phone-hub
```

### **2. Setup Environment Variables**
```bash
# Set JAVA_HOME (Windows)
set JAVA_HOME=C:\Program Files\Java\jdk-17

# Set JAVA_HOME (Linux/Mac)
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
```

### **3. Start Database với Docker**
```bash
# Start PostgreSQL và Redis
docker-compose up -d

# Verify services are running
docker-compose ps
```

### **4. Build Project**
```bash
# Using Maven wrapper
./mvnw clean compile

# Or using Maven directly
mvn clean compile
```

### **5. Run Application**
```bash
# Build WAR file
./mvnw clean package

# Deploy to Tomcat (if using external Tomcat)
# Copy target/ute-phone-hub.war to Tomcat webapps/
```

---

## 🐳 **Docker Setup**

### **Development Environment**
```bash
# Start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop services
docker-compose down
```

### **Services Included**
- **PostgreSQL**: `localhost:5432`
  - Database: `utephonehub_dev`
  - User: `utephonehub_user`
  - Password: `utephonehub_password`
- **Redis**: `localhost:6379`

---

## 🗄️ **Database Setup**

### **Manual PostgreSQL Setup**
```sql
-- Create database
CREATE DATABASE utephonehub_dev;

-- Create user
CREATE USER utephonehub_user WITH PASSWORD 'utephonehub_password';

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE utephonehub_dev TO utephonehub_user;
```

### **Database Schema**
Database schema sẽ được tự động tạo khi chạy ứng dụng lần đầu với `hibernate.hbm2ddl.auto=update`.

---

## 🔧 **Configuration**

### **Database Configuration**
File: `src/main/resources/META-INF/persistence.xml`
```xml
<property name="jakarta.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/utephonehub_dev"/>
<property name="jakarta.persistence.jdbc.user" value="utephonehub_user"/>
<property name="jakarta.persistence.jdbc.password" value="utephonehub_password"/>
```

### **Logging Configuration**
File: `src/main/resources/log4j2.xml`
- Console logging: Enabled
- File logging: `logs/utephonehub.log`
- Error logging: `logs/error.log`

---

## 📁 **Project Structure**

```
ute-phone-hub/
├── src/
│   ├── main/
│   │   ├── java/com/utephonehub/
│   │   │   ├── controller/     # Servlet controllers
│   │   │   ├── service/        # Business logic
│   │   │   ├── repository/     # Data access
│   │   │   ├── entity/         # JPA entities
│   │   │   ├── dto/            # Data transfer objects
│   │   │   │   ├── request/    # Request DTOs
│   │   │   │   └── response/   # Response DTOs
│   │   │   ├── config/         # Configuration classes
│   │   │   ├── util/           # Utility classes
│   │   │   ├── exception/      # Custom exceptions
│   │   │   └── filter/         # Servlet filters
│   │   ├── resources/
│   │   │   ├── META-INF/
│   │   │   │   └── persistence.xml
│   │   │   └── log4j2.xml
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   ├── views/      # JSP pages
│   │       │   └── web.xml
│   │       ├── static/         # Static assets
│   │       │   ├── css/
│   │       │   ├── js/
│   │       │   └── images/
│   │       └── index.jsp
│   └── test/                   # Test classes
├── docker/
│   └── postgres/
│       └── init.sql
├── docs/                       # Documentation
├── docker-compose.yml
├── pom.xml
└── README.md
```

---

## 🧪 **Testing**

### **Unit Tests**
```bash
# Run all tests
./mvnw test

# Run specific test class
./mvnw test -Dtest=UserServiceTest
```

### **API Testing**
1. Import Postman collection: `docs/api/postman-collection.json`
2. Import environment: `docs/api/postman-environment.json`
3. Start application
4. Run test requests

---

## 🚨 **Troubleshooting**

### **Common Issues**

#### **1. JAVA_HOME not set**
```bash
# Windows
set JAVA_HOME=C:\Program Files\Java\jdk-17

# Linux/Mac
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
```

#### **2. Database Connection Issues**
```bash
# Check if PostgreSQL is running
docker-compose ps

# Check database logs
docker-compose logs postgres

# Test connection
docker-compose exec postgres psql -U utephonehub_user -d utephonehub_dev
```

#### **3. Port Conflicts**
```bash
# Check port usage
netstat -tulpn | grep :5432
netstat -tulpn | grep :6379

# Kill process using port
sudo kill -9 $(lsof -t -i:5432)
```

#### **4. Maven Build Issues**
```bash
# Clean Maven cache
./mvnw clean

# Update dependencies
./mvnw dependency:resolve

# Skip tests
./mvnw clean package -DskipTests
```

---

## 📊 **Development Workflow**

### **1. Daily Development**
```bash
# Start services
docker-compose up -d

# Build project
./mvnw clean compile

# Run tests
./mvnw test

# Package application
./mvnw clean package
```

### **2. Database Changes**
```bash
# Update init.sql
# Recreate database
docker-compose down -v
docker-compose up -d
```

### **3. Code Changes**
```bash
# Make code changes
# Rebuild
./mvnw clean compile

# Test changes
./mvnw test
```

---

## 🔗 **Useful Commands**

### **Docker Commands**
```bash
# Start services
docker-compose up -d

# Stop services
docker-compose down

# View logs
docker-compose logs -f

# Restart specific service
docker-compose restart postgres

# Access PostgreSQL
docker-compose exec postgres psql -U utephonehub_user -d utephonehub_dev
```

### **Maven Commands**
```bash
# Clean and compile
./mvnw clean compile

# Run tests
./mvnw test

# Package application
./mvnw clean package

# Install to local repository
./mvnw clean install

# Skip tests
./mvnw clean package -DskipTests
```

---

## 📞 **Support**

### **Documentation**
- **API Documentation**: `docs/api/endpoints.md`
- **Database Schema**: `docs/database/schema.md`
- **Architecture**: `docs/architecture/overview.md`
- **Critical Rules**: `docs/critical-rules.md`

### **Getting Help**
- **Technical Issues**: Create issue với label `bug`
- **Setup Issues**: Create issue với label `setup`
- **Documentation**: Create issue với label `documentation`

---

**📝 Last Updated**: January 15, 2025  
**📝 Version**: 1.0  
**👥 Maintained by**: UTE-PhoneHub Development Team
