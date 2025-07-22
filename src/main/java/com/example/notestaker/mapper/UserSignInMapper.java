package com.example.notestaker.mapper;

import com.example.notestaker.model.response.UserSignInResponse;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserSignInMapper implements RowMapper<UserSignInResponse> {

    @Override
    public UserSignInResponse mapRow(ResultSet rs, int rowNum) throws SQLException {

        return UserSignInResponse.builder()
                .userId(rs.getInt("userId"))
                .userName(rs.getString("userName"))
                .userEmail(rs.getString("userEmail"))
                .userPassword(rs.getString("userPassword"))
                .build();
    }
}
