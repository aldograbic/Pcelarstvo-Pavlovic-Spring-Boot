package com.honeywebsite.project.classes.review;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ReviewRowMapper implements RowMapper<Review>{

    @Override
    public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
        Review review = new Review();
        review.setName(rs.getString("name"));
        review.setStars(rs.getInt("stars"));
        review.setMessage(rs.getString("message"));
        review.setCreatedAt(rs.getDate("created_at"));

        return review;
    }
}
