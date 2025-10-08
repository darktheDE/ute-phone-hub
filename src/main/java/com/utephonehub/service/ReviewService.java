package com.utephonehub.service;
import com.utephonehub.entity.Review;
import com.utephonehub.exception.NotFoundException;
import com.utephonehub.repository.ProductRepository;
import com.utephonehub.repository.ReviewRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewService {
    private static final Logger logger = LogManager.getLogger(ReviewService.class);
    private final ReviewRepository reviewRepository = new ReviewRepository();
    private final ProductRepository productRepository = new ProductRepository();

    public Map<String, Object> getReviewsByProduct(Long productId, int page, int limit) throws Exception {
        logger.info("Fetching reviews for product {} (page {}, limit {})", productId, page, limit);

        if (productRepository.findById(productId).isEmpty()) {
            throw new NotFoundException("Sản phẩm không tồn tại");
        }

        List<Review> reviews = reviewRepository.findByProductId(productId, page, limit);
        long total = reviewRepository.countByProduct(productId);
        int totalPages = (int) Math.ceil((double) total / limit);

        Map<String, Object> pagination = Map.of(
            "page", page,
            "limit", limit,
            "totalItems", total,
            "totalPages", totalPages
        );

        Map<String, Object> result = new HashMap<>();
        result.put("items", reviews);
        result.put("pagination", pagination);
        return result;
    }
}
