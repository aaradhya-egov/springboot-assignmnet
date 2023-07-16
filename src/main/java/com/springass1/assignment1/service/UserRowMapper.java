package com.springass1.assignment1.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springass1.assignment1.model.Address;
import com.springass1.assignment1.model.EgovUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<EgovUser> {
    @Override
    public EgovUser mapRow(@org.jetbrains.annotations.NotNull ResultSet rs, int rowNum) throws SQLException {
         EgovUser x = new EgovUser();
         x.setId(rs.getString("id"));
         x.setName(rs.getString("name"));
         x.setGender(rs.getString("gender"));
         x.setMobilenumber(rs.getString("mobilenumber"));
        String addressJson = rs.getString("address");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Address address = objectMapper.readValue(addressJson, Address.class);
            x.setAddress(address);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error", e);
        }
         x.setActive(rs.getBoolean("active"));
         x.setCreatedtime(rs.getLong("createdtime"));
         return x;
    }
}
