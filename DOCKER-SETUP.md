# UTE Phone Hub - Docker Development Setup

## 🚀 Quick Start

### 1. Start All Services
```bash
docker-compose up -d
```

### 2. Check Status
```bash
docker-compose ps
```

### 3. View Logs
```bash
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f app
docker-compose logs -f postgres
```

### 4. Stop Services
```bash
docker-compose down
```

## 🌐 Access URLs

### **Main Application**
- **URL**: http://localhost:8080
- **Description**: UTE Phone Hub main application

### **API Endpoints**
- **Health Check**: http://localhost:8080/api/v1/health
- **Register**: http://localhost:8080/api/v1/auth/register
- **Login**: http://localhost:8080/api/v1/auth/login

### **Database**
- **Host**: localhost:5432
- **Database**: utephonehub_dev
- **User**: utephonehub_user
- **Password**: utephonehub_password

### **Redis**
- **Host**: localhost:6379

## 🔧 Development Commands

### Rebuild Application
```bash
docker-compose build app
docker-compose up -d app
```

### Database Commands
```bash
# Access PostgreSQL
docker-compose exec postgres psql -U utephonehub_user -d utephonehub_dev

# Access Redis
docker-compose exec redis redis-cli
```

### Application Commands
```bash
# View application logs
docker-compose logs -f app

# Restart application
docker-compose restart app

# Execute commands in app container
docker-compose exec app bash
```

## 📊 Monitoring

### Health Checks
- **Application**: http://localhost:8080/api/v1/health
- **PostgreSQL**: `docker-compose exec postgres pg_isready`
- **Redis**: `docker-compose exec redis redis-cli ping`

### Logs Location
- **Application logs**: `./logs/` directory
- **Container logs**: `docker-compose logs`

## 🛠️ Troubleshooting

### Common Issues

#### 1. Port Already in Use
```bash
# Check port usage
netstat -tulpn | grep :8080
netstat -tulpn | grep :5432

# Kill process using port
sudo kill -9 $(lsof -t -i:8080)
```

#### 2. Database Connection Issues
```bash
# Check database status
docker-compose exec postgres pg_isready -U utephonehub_user -d utephonehub_dev

# Reset database
docker-compose down -v
docker-compose up -d
```

#### 3. Application Build Issues
```bash
# Clean build
docker-compose build --no-cache app
docker-compose up -d app
```

#### 4. Permission Issues
```bash
# Fix logs directory permissions
sudo chown -R $USER:$USER logs/
```

## 📝 Environment Variables

The application uses these environment variables:

- `DB_HOST`: Database host (default: postgres)
- `DB_PORT`: Database port (default: 5432)
- `DB_NAME`: Database name (default: utephonehub_dev)
- `DB_USER`: Database user (default: utephonehub_user)
- `DB_PASSWORD`: Database password (default: utephonehub_password)
- `REDIS_HOST`: Redis host (default: redis)
- `REDIS_PORT`: Redis port (default: 6379)

## 🔄 Development Workflow

1. **Make code changes**
2. **Rebuild application**: `docker-compose build app`
3. **Restart application**: `docker-compose restart app`
4. **Test changes**: Visit http://localhost:8080

## 📞 Support

If you encounter any issues:
1. Check logs: `docker-compose logs -f`
2. Verify health: http://localhost:8080/api/v1/health
3. Check container status: `docker-compose ps`
