#!/bin/bash

# UTE Phone Hub - Docker Development Script

echo "🚀 Starting UTE Phone Hub Development Environment..."

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker is not running. Please start Docker first."
    exit 1
fi

# Check if docker-compose is available
if ! command -v docker-compose &> /dev/null; then
    echo "❌ docker-compose is not installed. Please install docker-compose first."
    exit 1
fi

echo "📦 Building and starting services..."

# Build and start services
docker-compose up -d --build

# Wait for services to be ready
echo "⏳ Waiting for services to be ready..."
sleep 30

# Check service status
echo "📊 Checking service status..."
docker-compose ps

# Check health
echo "🏥 Checking application health..."
if curl -f http://localhost:8080/api/v1/health > /dev/null 2>&1; then
    echo "✅ Application is healthy!"
else
    echo "⚠️  Application health check failed. Check logs with: docker-compose logs -f app"
fi

echo ""
echo "🎉 UTE Phone Hub is ready!"
echo ""
echo "🌐 Access URLs:"
echo "   Main Application: http://localhost:8080"
echo "   Health Check:     http://localhost:8080/api/v1/health"
echo "   API Register:     http://localhost:8080/api/v1/auth/register"
echo "   API Login:        http://localhost:8080/api/v1/auth/login"
echo ""
echo "📊 Database:"
echo "   Host: localhost:5432"
echo "   Database: utephonehub_dev"
echo "   User: utephonehub_user"
echo "   Password: utephonehub_password"
echo ""
echo "🔧 Useful Commands:"
echo "   View logs:        docker-compose logs -f"
echo "   Stop services:    docker-compose down"
echo "   Restart app:      docker-compose restart app"
echo "   Rebuild app:      docker-compose build app"
echo ""
