package com.utephonehub.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis Configuration
 * Singleton pattern for Redis connection management
 */
public class RedisConfig {
    
    private static final Logger logger = LogManager.getLogger(RedisConfig.class);
    private static JedisPool jedisPool;
    
    static {
        initializeJedisPool();
    }
    
    /**
     * Initialize Jedis Pool
     */
    private static void initializeJedisPool() {
        try {
            // Load environment variables
            String redisHost = getEnvOrProperty("REDIS_HOST", "localhost");
            int redisPort = Integer.parseInt(getEnvOrProperty("REDIS_PORT", "6379"));
            String redisPassword = getEnvOrProperty("REDIS_PASSWORD", null);
            
            // Configure pool
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(20);
            poolConfig.setMaxIdle(10);
            poolConfig.setMinIdle(5);
            poolConfig.setTestOnBorrow(true);
            poolConfig.setTestOnReturn(true);
            poolConfig.setTestWhileIdle(true);
            
            // Create pool
            if (redisPassword != null && !redisPassword.isEmpty()) {
                jedisPool = new JedisPool(poolConfig, redisHost, redisPort, 2000, redisPassword);
            } else {
                jedisPool = new JedisPool(poolConfig, redisHost, redisPort, 2000);
            }
            
            // Test connection
            try (Jedis jedis = jedisPool.getResource()) {
                jedis.ping();
                logger.info("Redis connection established successfully at {}:{}", redisHost, redisPort);
            }
            
        } catch (Exception e) {
            logger.error("Failed to initialize Redis connection", e);
            throw new RuntimeException("Redis initialization failed", e);
        }
    }
    
    /**
     * Get Jedis resource from pool
     * Remember to close the resource after use
     */
    public static Jedis getJedis() {
        if (jedisPool == null) {
            throw new IllegalStateException("Jedis pool is not initialized");
        }
        return jedisPool.getResource();
    }
    
    /**
     * Close Jedis pool
     */
    public static void closePool() {
        if (jedisPool != null && !jedisPool.isClosed()) {
            jedisPool.close();
            logger.info("Redis pool closed");
        }
    }
    
    /**
     * Get environment variable or system property
     */
    private static String getEnvOrProperty(String key, String defaultValue) {
        String value = System.getenv(key);
        if (value == null) {
            value = System.getProperty(key);
        }
        return value != null ? value : defaultValue;
    }
}
