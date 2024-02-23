package com.honeywebsite.project.classes.product;

import java.util.List;

public interface ProductRepository {

    List<Product> getProducts();

    Product getProductById(int id);

    List<Product> getFeaturedProducts();
    
    List<Product> filterByTypeAndPrice(String type, Double minPrice, Double maxPrice);
}