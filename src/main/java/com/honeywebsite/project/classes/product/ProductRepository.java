package com.honeywebsite.project.classes.product;

import java.util.List;

import com.honeywebsite.project.classes.purchase.Purchase;

public interface ProductRepository {
    List<Product> getProducts();
    Product getProductById(int id);
    List<Product> getFeaturedProducts();
    void savePurchaseDetails(Purchase purchase);
    List<Product> filterByTypeAndPrice(String type, Double minPrice, Double maxPrice);
}
