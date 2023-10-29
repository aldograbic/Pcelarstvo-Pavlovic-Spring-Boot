package com.honeywebsite.project.classes.contact;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcContactRepository implements ContactRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcContactRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveMessage(Contact contact) {
        String sql = "INSERT INTO messages (name, email, message) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, contact.getName(), contact.getEmail(), contact.getMessage());
    }
}
