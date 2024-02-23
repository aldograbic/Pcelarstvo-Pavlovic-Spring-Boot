package com.honeywebsite.project.classes.product;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

public class ProductRowMapper implements RowMapper<Product>{

    @Override
    public Product mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setType(rs.getString("type"));
        product.setKilograms(rs.getBigDecimal("kilograms"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setImage(rs.getString("image"));
        product.setOnStock(rs.getBoolean("on_stock"));
        product.setIsFeatured(rs.getBoolean("is_featured"));

        return product;
    }
}