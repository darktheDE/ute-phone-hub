package com.utephonehub.controller;

import com.utephonehub.exception.NotFoundException;
import com.utephonehub.service.ReviewService;
import com.utephonehub.util.JsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Review Controller
 * Xử lý request: GET /api/v1/products/{id}/reviews
 */
@WebServlet("/api/v1/products/*")
public class ReviewController extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(ReviewController.class);
    private final ReviewService reviewService;
    private final JsonUtil jsonUtil;

    public ReviewController() {
        this.reviewService = new ReviewService();
        this.jsonUtil = new JsonUtil();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo(); // e.g. /123/reviews
        logger.info("ReviewController GET request: {}", pathInfo);

        try {
            // Kiểm tra endpoint hợp lệ
            if (pathInfo == null || !pathInfo.matches("^/\\d+/reviews$")) {
                sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, "Endpoint không tồn tại");
                return;
            }

            // Lấy productId từ path
            Long productId = Long.parseLong(pathInfo.split("/")[1]);

            // Lấy tham số phân trang
            int page = parseInt(request.getParameter("page"), 1);
            int limit = parseInt(request.getParameter("limit"), 5);

            // Gọi service lấy dữ liệu
            Map<String, Object> result = reviewService.getReviewsByProduct(productId, page, limit);

            // Chuẩn bị dữ liệu phản hồi
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("success", true);
            responseData.put("message", "Lấy danh sách đánh giá thành công");
            responseData.put("data", result.get("items"));
            responseData.put("metadata", Map.of("pagination", result.get("pagination")));

            // Gửi phản hồi JSON
            sendJsonResponse(response, HttpServletResponse.SC_OK, responseData);

        } catch (NotFoundException e) {
            logger.warn("Product not found: {}", e.getMessage());
            sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (NumberFormatException e) {
            logger.warn("Invalid product ID format: {}", pathInfo);
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "ID sản phẩm không hợp lệ");
        } catch (Exception e) {
            logger.error("Unexpected error fetching reviews", e);
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Đã xảy ra lỗi hệ thống");
        }
    }

    /**
     * Gửi JSON response thành công
     */
    private void sendJsonResponse(HttpServletResponse response, int statusCode, Object data)
            throws IOException {

        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String jsonResponse = jsonUtil.toJson(data);
        response.getWriter().write(jsonResponse);

        logger.debug("Sent JSON response: {}", jsonResponse);
    }

    /**
     * Gửi error response
     */
    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message)
            throws IOException {

        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("message", message);

        String jsonResponse = jsonUtil.toJson(errorResponse);
        response.getWriter().write(jsonResponse);

        logger.debug("Sent error response: {}", jsonResponse);
    }

    private int parseInt(String value, int defaultValue) {
        try {
            return (value == null) ? defaultValue : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
