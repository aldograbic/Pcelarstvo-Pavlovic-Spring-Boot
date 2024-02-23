package com.honeywebsite.project.classes.product;

import java.math.BigDecimal;

public class Product {
    private int id;
    private String name;
    private String type;
    private int grams;
    private String description;
    private BigDecimal price;
    private String image;
    private boolean onStock;
    private boolean isFeatured;

    public Product() {}

    public Product(int id, String name, String type, int grams, String description, BigDecimal price, String image, boolean onStock, boolean isFeatured) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.grams = grams;
        this.description = description;
        this.price = price;
        this.image = image;
        this.onStock = onStock;
        this.isFeatured = isFeatured;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getGrams() {
        return grams;
    }

    public void setGrams(int grams) {
        this.grams = grams;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isOnStock() {
        return onStock;
    }

    public void setOnStock(boolean onStock) {
        this.onStock = onStock;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(boolean isFeatured) {
        this.isFeatured = isFeatured;
    }
}
