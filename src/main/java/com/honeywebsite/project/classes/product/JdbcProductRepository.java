package com.honeywebsite.project.classes.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcProductRepository implements ProductRepository{
    
    private final JdbcTemplate jdbcTemplate;

    public JdbcProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> getProducts() {
        String sql = "SELECT id, name, type, kilograms, description, price, image, on_stock, is_featured FROM products";

        return jdbcTemplate.query(sql, new ProductRowMapper());
    }

    @Override
    public Product getProductById(int id) {
        String sql = "SELECT id, name, type, kilograms, description, price, image, on_stock, is_featured FROM products WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, new ProductRowMapper(), id);
    }

    @Override
    public List<Product> getFeaturedProducts() {
        String sql = "SELECT id, name, type, kilograms, description, price, image, on_stock, is_featured FROM products WHERE is_featured = 1 LIMIT 4";

        return jdbcTemplate.query(sql, new ProductRowMapper());
    }
    
    @SuppressWarnings("null")
    @Override
    public List<Product> filterByTypeAndPrice(String type, Double minPrice, Double maxPrice) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT id, name, type, kilograms, description, price, image, on_stock, is_featured FROM products WHERE 1=1");

        List<Object> params = new ArrayList<>();

        if (type != null) {
            sqlBuilder.append(" AND type = ?");
            params.add(type);
        }

        if (minPrice != null) {
            sqlBuilder.append(" AND price >= ?");
            params.add(minPrice);
        }

        if (maxPrice != null) {
            sqlBuilder.append(" AND price <= ?");
            params.add(maxPrice);
        }

        String sql = sqlBuilder.toString();

        return jdbcTemplate.query(sql, new ProductRowMapper(), params.toArray());
    }
}