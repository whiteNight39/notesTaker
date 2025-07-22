package com.example.notestaker.repository.database.query;

public class UserAuthQuery {

    public static final String CREATE_USER_AUTH = """
            INSERT INTO FRED_NOTE_USER_AUTH (authUserId, authJwtToken, authIssuedAt, authExpiresAt, authIsValid, authUserDeviceIp, authUserDeviceAgent)
            VALUES (:authUserId, :authJwtToken, :authIssuedAt, :authExpiresAt, :authIsValid,  :authUserDeviceIp, :authUserDeviceAgent)
            """;

    public static final String GET_USER_AUTH = """
            SELECT * FROM FRED_NOTE_USER_AUTH
            WHERE authUserId = :authUserId
                AND authUserDeviceIp = :authUserDeviceIp
                AND authUserDeviceAgent = :authUserDeviceAgent
                AND authIsValid = 1;
            """;
}
