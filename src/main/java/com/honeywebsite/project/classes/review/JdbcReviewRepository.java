package com.honeywebsite.project.classes.review;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcReviewRepository implements ReviewRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcReviewRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public void saveProductReview(Review review) {
        String sql = "INSERT INTO reviews (name, stars, message, product_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, review.getName(), review.getStars(), review.getMessage(), review.getProductId());
    }

    @SuppressWarnings("unused")
    @Override
    public double getAverageReviewById(int productId) {
        String sql = "SELECT ROUND(AVG(stars), 2) FROM reviews WHERE product_id = ?";
        Double result = jdbcTemplate.queryForObject(sql, Double.class, productId);

        if (result != null) {
            return result.doubleValue();
        } else {
            return 0.0;
        }
    }

    @Override
    public List<Review> getLimitedProductReviews(int productId) {
        String sql = "SELECT name, stars, message, created_at FROM reviews WHERE product_id = ? ORDER BY stars DESC LIMIT 3";
        return jdbcTemplate.query(sql, new ReviewRowMapper(), productId);
    }

    @Override
    public List<Review> getAllProductReviews(int productId) {
        String sql = "SELECT name, stars, message, created_at FROM reviews WHERE product_id = ?";
        return jdbcTemplate.query(sql, new ReviewRowMapper(), productId);
    }
}
