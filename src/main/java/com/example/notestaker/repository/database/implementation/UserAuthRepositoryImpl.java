package com.example.notestaker.repository.database.implementation;

import com.example.notestaker.model.entity.UserAuth;
import com.example.notestaker.repository.database.interfaces.UserAuthRepository;
import com.example.notestaker.repository.database.query.UserAuthQuery;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserAuthRepositoryImpl implements UserAuthRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserAuthRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createUserAuth(UserAuth userAuth) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("authUserId", userAuth.getAuthUserId())
                .addValue("authJwtToken", userAuth.getAuthJwtToken())
                .addValue("authIssuedAt", userAuth.getAuthIssuedAt())
                .addValue("authExpiresAt", userAuth.getAuthExpiresAt())
                .addValue("authIsValid", userAuth.getAuthIsValid())
                .addValue("authUserDeviceIp", userAuth.getAuthUserDeviceIp())
                .addValue("authUserDeviceAgent", userAuth.getAuthUserDeviceAgent());

        jdbcTemplate.update(UserAuthQuery.CREATE_USER_AUTH, params);
    }

    @Override
    public UserAuth getUserAuth(Integer userId, String ip, String userAgent) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("authUserId", userId)
                .addValue("authUserDeviceIp", ip)
                .addValue("authUserDeviceAgent", userAgent);

        List<UserAuth> auths = jdbcTemplate.query(UserAuthQuery.GET_USER_AUTH, params,
                (rs, rowNum) -> UserAuth.builder()
                        .authId(rs.getInt("authId"))
                        .authUserId(rs.getInt("authUserId"))
                        .authJwtToken(rs.getString("authJwtToken"))
                        .authIssuedAt(rs.getDate("authIssuedAt"))
                        .authExpiresAt(rs.getDate("authExpiresAt"))
                        .authIsValid(rs.getBoolean("authIsValid"))
                        .authUserDeviceIp(rs.getString("authUserDeviceIp"))
                        .authUserDeviceAgent(rs.getString("authUserDeviceAgent"))
                        .build());
        return auths.isEmpty() ? null : auths.getFirst();
    }
}
