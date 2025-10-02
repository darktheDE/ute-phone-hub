package com.utephonehub.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 * JSON Utility
 * Xử lý parse và serialize JSON
 */
public class JsonUtil {
    
    private static final Logger logger = LogManager.getLogger(JsonUtil.class);
    private final Gson gson;
    
    public JsonUtil() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }
    
    /**
     * Parse JSON từ HttpServletRequest
     * @param request HttpServletRequest
     * @param clazz Class type
     * @return Parsed object
     */
    public <T> T parseJson(HttpServletRequest request, Class<T> clazz) throws IOException {
        try {
            StringBuilder jsonBuilder = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            
            String jsonString = jsonBuilder.toString();
            logger.debug("Parsing JSON: {}", jsonString);
            
            return gson.fromJson(jsonString, clazz);
        } catch (JsonSyntaxException e) {
            logger.error("Invalid JSON format", e);
            throw new IllegalArgumentException("Invalid JSON format");
        }
    }
    
    /**
     * Parse JSON từ HttpServletRequest với Type
     * @param request HttpServletRequest
     * @param type Type
     * @return Parsed object
     */
    public <T> T parseJson(HttpServletRequest request, Type type) throws IOException {
        try {
            StringBuilder jsonBuilder = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            
            String jsonString = jsonBuilder.toString();
            logger.debug("Parsing JSON: {}", jsonString);
            
            return gson.fromJson(jsonString, type);
        } catch (JsonSyntaxException e) {
            logger.error("Invalid JSON format", e);
            throw new IllegalArgumentException("Invalid JSON format");
        }
    }
    
    /**
     * Convert object to JSON string
     * @param object Object to convert
     * @return JSON string
     */
    public String toJson(Object object) {
        try {
            return gson.toJson(object);
        } catch (Exception e) {
            logger.error("Error converting object to JSON", e);
            throw new RuntimeException("Failed to convert object to JSON", e);
        }
    }
    
    /**
     * Convert object to pretty JSON string
     * @param object Object to convert
     * @return Pretty JSON string
     */
    public String toPrettyJson(Object object) {
        try {
            return gson.toJson(object);
        } catch (Exception e) {
            logger.error("Error converting object to pretty JSON", e);
            throw new RuntimeException("Failed to convert object to pretty JSON", e);
        }
    }
    
    /**
     * Parse JSON string to object
     * @param json JSON string
     * @param clazz Class type
     * @return Parsed object
     */
    public <T> T fromJson(String json, Class<T> clazz) {
        try {
            return gson.fromJson(json, clazz);
        } catch (JsonSyntaxException e) {
            logger.error("Invalid JSON format: {}", json, e);
            throw new IllegalArgumentException("Invalid JSON format");
        }
    }
    
    /**
     * Parse JSON string to object với Type
     * @param json JSON string
     * @param type Type
     * @return Parsed object
     */
    public <T> T fromJson(String json, Type type) {
        try {
            return gson.fromJson(json, type);
        } catch (JsonSyntaxException e) {
            logger.error("Invalid JSON format: {}", json, e);
            throw new IllegalArgumentException("Invalid JSON format");
        }
    }
    
    /**
     * Check if string is valid JSON
     * @param json JSON string
     * @return true if valid JSON
     */
    public boolean isValidJson(String json) {
        try {
            gson.fromJson(json, Object.class);
            return true;
        } catch (JsonSyntaxException e) {
            return false;
        }
    }
}
