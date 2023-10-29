package com.honeywebsite.project.classes.review;

import java.sql.Date;

public class Review {
    private String name;
    private int stars;
    private String message;
    private int productId;
    private Date createdAt;

    public Review() {};

    public Review(String name, int stars, String message, int productId, Date createdAt) {
        this.name = name;
        this.stars = stars;
        this.message = message;
        this.productId = productId;
        this.createdAt = createdAt;
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
