package com.utephonehub.repository;

import com.utephonehub.entity.User;
import com.utephonehub.config.DatabaseConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * User Repository
 * Quản lý các thao tác database cho User entity
 */
public class UserRepository {
    
    private static final Logger logger = LogManager.getLogger(UserRepository.class);
    
    /**
     * Lưu user mới
     * @param user User entity
     * @return User đã được lưu
     */
    public User save(User user) {
        EntityManager em = DatabaseConfig.getEntityManager();
        try {
            DatabaseConfig.beginTransaction();
            if (user.getId() == null) {
                em.persist(user);
                logger.info("Created new user: {}", user.getEmail());
            } else {
                user = em.merge(user);
                logger.info("Updated user: {}", user.getEmail());
            }
            DatabaseConfig.commitTransaction();
            return user;
        } catch (Exception e) {
            DatabaseConfig.rollbackTransaction();
            logger.error("Error saving user: {}", user.getEmail(), e);
            throw new RuntimeException("Failed to save user", e);
        }
    }
    
    /**
     * Tìm user theo ID
     * @param id User ID
     * @return Optional<User>
     */
    public Optional<User> findById(Long id) {
        EntityManager em = DatabaseConfig.getEntityManager();
        try {
            User user = em.find(User.class, id);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            logger.error("Error finding user by ID: {}", id, e);
            throw new RuntimeException("Failed to find user by ID", e);
        }
    }
    
    /**
     * Tìm user theo email
     * @param email User email
     * @return Optional<User>
     */
    public Optional<User> findByEmail(String email) {
        EntityManager em = DatabaseConfig.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);
            User user = query.getSingleResult();
            return Optional.ofNullable(user);
        } catch (Exception e) {
            logger.debug("User not found with email: {}", email);
            return Optional.empty();
        }
    }
    
    /**
     * Kiểm tra email đã tồn tại chưa
     * @param email Email cần kiểm tra
     * @return true nếu email đã tồn tại
     */
    public boolean existsByEmail(String email) {
        EntityManager em = DatabaseConfig.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class);
            query.setParameter("email", email);
            Long count = query.getSingleResult();
            return count > 0;
        } catch (Exception e) {
            logger.error("Error checking email existence: {}", email, e);
            throw new RuntimeException("Failed to check email existence", e);
        }
    }
    
    /**
     * Lấy tất cả users
     * @return List<User>
     */
    public List<User> findAll() {
        EntityManager em = DatabaseConfig.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u ORDER BY u.createdAt DESC", User.class);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding all users", e);
            throw new RuntimeException("Failed to find all users", e);
        }
    }
    
    /**
     * Lấy users với pagination
     * @param page Số trang (bắt đầu từ 0)
     * @param size Kích thước trang
     * @return List<User>
     */
    public List<User> findAll(int page, int size) {
        EntityManager em = DatabaseConfig.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u ORDER BY u.createdAt DESC", User.class);
            query.setFirstResult(page * size);
            query.setMaxResults(size);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding users with pagination", e);
            throw new RuntimeException("Failed to find users with pagination", e);
        }
    }
    
    /**
     * Đếm tổng số users
     * @return Tổng số users
     */
    public long count() {
        EntityManager em = DatabaseConfig.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(u) FROM User u", Long.class);
            return query.getSingleResult();
        } catch (Exception e) {
            logger.error("Error counting users", e);
            throw new RuntimeException("Failed to count users", e);
        }
    }
    
    /**
     * Xóa user
     * @param id User ID
     * @return true nếu xóa thành công
     */
    public boolean deleteById(Long id) {
        EntityManager em = DatabaseConfig.getEntityManager();
        try {
            DatabaseConfig.beginTransaction();
            User user = em.find(User.class, id);
            if (user != null) {
                em.remove(user);
                DatabaseConfig.commitTransaction();
                logger.info("Deleted user: {}", user.getEmail());
                return true;
            }
            DatabaseConfig.rollbackTransaction();
            return false;
        } catch (Exception e) {
            DatabaseConfig.rollbackTransaction();
            logger.error("Error deleting user by ID: {}", id, e);
            throw new RuntimeException("Failed to delete user", e);
        }
    }
    
    /**
     * Cập nhật trạng thái user
     * @param id User ID
     * @param status Trạng thái mới
     * @return true nếu cập nhật thành công
     */
    public boolean updateStatus(Long id, User.UserStatus status) {
        EntityManager em = DatabaseConfig.getEntityManager();
        try {
            DatabaseConfig.beginTransaction();
            User user = em.find(User.class, id);
            if (user != null) {
                user.setStatus(status);
                em.merge(user);
                DatabaseConfig.commitTransaction();
                logger.info("Updated user status: {} -> {}", user.getEmail(), status);
                return true;
            }
            DatabaseConfig.rollbackTransaction();
            return false;
        } catch (Exception e) {
            DatabaseConfig.rollbackTransaction();
            logger.error("Error updating user status: {}", id, e);
            throw new RuntimeException("Failed to update user status", e);
        }
    }
}
