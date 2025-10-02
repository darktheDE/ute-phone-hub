@echo off

REM UTE Phone Hub - Docker Development Script for Windows

echo 🚀 Starting UTE Phone Hub Development Environment...

REM Check if Docker is running
docker info >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Docker is not running. Please start Docker first.
    pause
    exit /b 1
)

REM Check if docker-compose is available
docker-compose --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ docker-compose is not installed. Please install docker-compose first.
    pause
    exit /b 1
)

echo 📦 Building and starting services...

REM Build and start services
docker-compose up -d --build

REM Wait for services to be ready
echo ⏳ Waiting for services to be ready...
timeout /t 30 /nobreak >nul

REM Check service status
echo 📊 Checking service status...
docker-compose ps

REM Check health
echo 🏥 Checking application health...
curl -f http://localhost:8080/api/v1/health >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ Application is healthy!
) else (
    echo ⚠️  Application health check failed. Check logs with: docker-compose logs -f app
)

echo.
echo 🎉 UTE Phone Hub is ready!
echo.
echo 🌐 Access URLs:
echo    Main Application: http://localhost:8080
echo    Health Check:     http://localhost:8080/api/v1/health
echo    API Register:     http://localhost:8080/api/v1/auth/register
echo    API Login:        http://localhost:8080/api/v1/auth/login
echo.
echo 📊 Database:
echo    Host: localhost:5432
echo    Database: utephonehub_dev
echo    User: utephonehub_user
echo    Password: utephonehub_password
echo.
echo 🔧 Useful Commands:
echo    View logs:        docker-compose logs -f
echo    Stop services:    docker-compose down
echo    Restart app:      docker-compose restart app
echo    Rebuild app:      docker-compose build app
echo.
pause
