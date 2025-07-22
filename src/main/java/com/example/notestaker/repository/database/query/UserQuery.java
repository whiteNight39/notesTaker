package com.example.notestaker.repository.database.query;

public class UserQuery {

    public static final String CREATE_USER = """
            INSERT INTO FRED_NOTE_USER (userName, userEmail, userPassword)
            VALUES (:userName, :userEmail, :userPassword)
            """;

    public static final String UPDATE_USER = """
            UPDATE FRED_NOTE_USER
            SET
                userName = COALESCE(NULLIF(:userName, ''), userName),
                userUpdatedAt = GETDATE()
            WHERE
                userId = :userId
                AND userStatus <> 'DELETED'
            """;

    public static final String UPDATE_USER_PASSWORD = """
            UPDATE FRED_NOTE_USER
            SET
                userPassword = :userNewPassword
            WHERE
                userId = :userId AND
                userStatus <> 'DELETED';
            """;

    public static final String GET_USER_BY_ID = """
            SELECT userName, userEmail, userPassword
            FROM FRED_NOTE_USER
            WHERE userId = :userId
                AND userStatus <> 'DELETED'
            """;

    public static final String GET_USER_BY_EMAIL = """
            SELECT userId, userName, userEmail, userPassword
            FROM FRED_NOTE_USER
            WHERE LOWER(userEmail) = LOWER(:userEmail)
                AND userStatus <> 'DELETED'
            """;

    public static final String DELETE_USER = """
            UPDATE FRED_NOTE_USER
            SET userStatus = 'DELETED'
            WHERE userId = :userId
            """;
}
