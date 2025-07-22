package com.example.notestaker.repository.database.query;

public class NoteQuery {

    public static final String CREATE_NOTE = """
            INSERT INTO FRED_NOTES (noteTitle, noteContent, noteUserId)
            VALUES (
                COALESCE(NULLIF(:noteTitle, ''), 'New Note'),
                COALESCE(NULLIF(:noteContent, ''), ''),
                :noteUserId)
            """;

    public static final String UPDATE_NOTE = """
            UPDATE FRED_NOTES
            SET
                noteTitle = COALESCE(NULLIF(:noteTitle, ''), noteTitle),
                noteContent = COALESCE(NULLIF(:noteContent, ''), noteContent),
                noteUpdatedAt = GETDATE()
            WHERE
                noteId = :noteId
                AND noteUserId = :noteUserId
                AND noteStatus <> 'DELETED'
            """;

    public static final String GET_MY_NOTES = """
            SELECT noteId, noteTitle, noteContent, noteUserId, noteUpdatedAt
            FROM FRED_NOTES
            WHERE noteUserId = :noteUserId
                AND noteStatus <> 'DELETED'
            """;

    public static final String GET_MY_NOTE_BY_ID = """
            SELECT noteId, noteTitle, noteContent, noteUserId, noteUpdatedAt
            FROM FRED_NOTES
            WHERE noteUserId = :noteUserId
                AND noteId = :noteId
                AND noteStatus <> 'DELETED'
            """;

    public static final String GET_NOTE_BY_ID = """
            SELECT noteId, noteTitle, noteContent, noteUserId, noteUpdatedAt
            FROM FRED_NOTES
            WHERE noteUserId = :noteUserId
                AND noteId = :noteId
                AND noteStatus <> 'DELETED'
            """;

    public static final String GET_MY_NOTES_BY_QUERY = """
            SELECT noteId, noteTitle, noteContent, noteUserId, noteUpdatedAt
            FROM FRED_NOTES
            WHERE noteUserId = :noteUserId
                AND noteStatus <> 'DELETED'
                AND (
                    noteTitle LIKE '%' + :query + '%' OR
                    noteContent LIKE '%' + :query + '%'
                )
            """;

    public static final String DELETE_NOTE = """
            UPDATE FRED_NOTES
            SET noteStatus = 'DELETED'
            WHERE noteId = :noteId AND noteUserId = :noteUserId;
            """;
}
