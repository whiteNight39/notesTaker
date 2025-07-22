package com.example.notestaker.repository.database.implementation;

import com.example.notestaker.mapper.UserMapper;
import com.example.notestaker.mapper.UserSignInMapper;
import com.example.notestaker.model.entity.User;
import com.example.notestaker.model.response.UserResponse;
import com.example.notestaker.model.response.UserSignInResponse;
import com.example.notestaker.repository.database.interfaces.UserRepository;
import com.example.notestaker.repository.database.query.UserQuery;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createUser(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userName", user.getUserName())
                .addValue("userEmail", user.getUserEmail())
                .addValue("userPassword", user.getUserPassword());

        jdbcTemplate.update(UserQuery.CREATE_USER, params);
    }

    @Override
    public List<UserSignInResponse> getUserByEmail(String userEmail) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userEmail", userEmail);

        List<UserSignInResponse> responseList = jdbcTemplate.query(UserQuery.GET_USER_BY_EMAIL, params, new UserSignInMapper());
        return responseList.isEmpty() ? null : responseList;
    }

    @Override
    public void updateUser(String userName, int userId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userName", userName)
                .addValue("userId", userId);

        jdbcTemplate.update(UserQuery.UPDATE_USER, params);
    }

    @Override
    public void updateUserPassword(int userId, String userNewPassword) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("userNewPassword", userNewPassword);

        jdbcTemplate.update(UserQuery.UPDATE_USER_PASSWORD, params);
    }

    @Override
    public UserResponse getUserById(int userId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId);

        List<UserResponse> results = jdbcTemplate.query(UserQuery.GET_USER_BY_ID, params, new UserMapper());

        return results.isEmpty() ? null : results.getFirst();
    }

    @Override
    public void deleteUser(int userId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId);

        jdbcTemplate.update(UserQuery.DELETE_USER, params);
    }
}
