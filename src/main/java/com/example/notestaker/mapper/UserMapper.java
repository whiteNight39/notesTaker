package com.example.notestaker.mapper;

import com.example.notestaker.model.entity.User;
import com.example.notestaker.model.response.UserResponse;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserMapper implements RowMapper<UserResponse> {

    @Override
    public UserResponse mapRow(ResultSet rs, int rowNum) throws SQLException {

        return UserResponse.builder()
                .userName(rs.getString("userName"))
                .userEmail(rs.getString("userEmail"))
                .userPassword(rs.getString("userPassword"))

                .build();
    }
}
