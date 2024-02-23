package com.honeywebsite.project.classes.review;

import java.util.List;

public interface ReviewRepository {
    void saveProductReview(Review review);
    double getAverageReviewById(int productId);
    List<Review> getLimitedProductReviews(int productId);
    List<Review> getAllProductReviews(int productId);
}