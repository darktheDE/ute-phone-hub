package com.utephonehub.repository;

import com.utephonehub.config.DatabaseConfig;
import com.utephonehub.entity.Review;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * Review Repository
 * Quản lý thao tác database cho Review entity
 */
public class ReviewRepository {

    private static final Logger logger = LogManager.getLogger(ReviewRepository.class);

    /**
     * Lấy danh sách review theo product ID (có phân trang)
     */
    public List<Review> findByProductId(Long productId, int page, int size) {
        EntityManager em = DatabaseConfig.getEntityManager();
        try {
            TypedQuery<Review> query = em.createQuery(
                "SELECT r FROM Review r " +
                "JOIN FETCH r.user u " +
                "WHERE r.product.id = :productId " +
                "ORDER BY r.createdAt DESC", Review.class
            );
            query.setParameter("productId", productId);
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);
            List<Review> result = query.getResultList();

            logger.info("Found {} reviews for product {}", result.size(), productId);
            return result;
        } catch (Exception e) {
            logger.error("Error finding reviews for product {}", productId, e);
            throw new RuntimeException("Failed to find reviews by product", e);
        }
    }

    /**
     * Đếm tổng số review của product
     */
    public long countByProduct(Long productId) {
        EntityManager em = DatabaseConfig.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(r) FROM Review r WHERE r.product.id = :productId", Long.class
            );
            query.setParameter("productId", productId);
            return query.getSingleResult();
        } catch (Exception e) {
            logger.error("Error counting reviews for product {}", productId, e);
            throw new RuntimeException("Failed to count reviews", e);
        }
    }
}
