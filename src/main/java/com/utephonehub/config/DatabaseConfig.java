package com.utephonehub.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Database Configuration Manager
 * Quản lý kết nối database và EntityManager
 */
public class DatabaseConfig {
    
    private static final Logger logger = LogManager.getLogger(DatabaseConfig.class);
    private static final String PERSISTENCE_UNIT_NAME = "ute-phone-hub-pu";
    
    private static EntityManagerFactory entityManagerFactory;
    private static final ThreadLocal<EntityManager> entityManagerThreadLocal = new ThreadLocal<>();
    
    static {
        initializeEntityManagerFactory();
    }
    
    /**
     * Khởi tạo EntityManagerFactory
     */
    private static void initializeEntityManagerFactory() {
        try {
            logger.info("Initializing EntityManagerFactory...");
            
            // Tạo properties map với environment variables override
            Map<String, String> properties = new HashMap<>();
            
            // Database connection từ environment variables
            String dbUrl = System.getenv("DB_URL");
            String dbUser = System.getenv("DB_USER");
            String dbPassword = System.getenv("DB_PASSWORD");
            
            if (dbUrl != null) {
                properties.put("jakarta.persistence.jdbc.url", dbUrl);
                logger.info("Using database URL from environment: {}", dbUrl);
            }
            if (dbUser != null) {
                properties.put("jakarta.persistence.jdbc.user", dbUser);
                logger.info("Using database user from environment: {}", dbUser);
            }
            if (dbPassword != null) {
                properties.put("jakarta.persistence.jdbc.password", dbPassword);
                logger.info("Using database password from environment");
            }
            
            entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, properties);
            logger.info("EntityManagerFactory initialized successfully");
        } catch (Exception e) {
            logger.error("Failed to initialize EntityManagerFactory", e);
            throw new RuntimeException("Database initialization failed", e);
        }
    }
    
    /**
     * Lấy EntityManager cho thread hiện tại
     * @return EntityManager instance
     */
    public static EntityManager getEntityManager() {
        EntityManager em = entityManagerThreadLocal.get();
        if (em == null || !em.isOpen()) {
            em = entityManagerFactory.createEntityManager();
            entityManagerThreadLocal.set(em);
            logger.debug("Created new EntityManager for thread: {}", Thread.currentThread().getName());
        } else {
            // Clear cache để tránh stale data
            em.clear();
            logger.debug("Cleared EntityManager cache for thread: {}", Thread.currentThread().getName());
        }
        return em;
    }
    
    /**
     * Đóng EntityManager cho thread hiện tại
     */
    public static void closeEntityManager() {
        EntityManager em = entityManagerThreadLocal.get();
        if (em != null && em.isOpen()) {
            em.close();
            entityManagerThreadLocal.remove();
            logger.debug("Closed EntityManager for thread: {}", Thread.currentThread().getName());
        }
    }
    
    /**
     * Bắt đầu transaction
     */
    public static void beginTransaction() {
        EntityManager em = getEntityManager();
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
            logger.debug("Transaction started");
        }
    }
    
    /**
     * Commit transaction
     */
    public static void commitTransaction() {
        EntityManager em = getEntityManager();
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
            logger.debug("Transaction committed");
        }
    }
    
    /**
     * Rollback transaction
     */
    public static void rollbackTransaction() {
        EntityManager em = getEntityManager();
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
            logger.debug("Transaction rolled back");
        }
    }
    
    /**
     * Đóng EntityManagerFactory
     */
    public static void shutdown() {
        try {
            closeEntityManager();
            if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
                entityManagerFactory.close();
                logger.info("EntityManagerFactory closed successfully");
            }
        } catch (Exception e) {
            logger.error("Error closing EntityManagerFactory", e);
        }
    }
    
    /**
     * Kiểm tra kết nối database
     * @return true nếu kết nối thành công
     */
    public static boolean testConnection() {
        try {
            EntityManager em = getEntityManager();
            em.createNativeQuery("SELECT 1").getSingleResult();
            logger.info("Database connection test successful");
            return true;
        } catch (Exception e) {
            logger.error("Database connection test failed", e);
            return false;
        }
    }
}
