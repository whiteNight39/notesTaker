package com.example.notestaker.mapper;

import com.example.notestaker.model.response.NoteResponse;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class NoteMapper implements RowMapper<NoteResponse> {

    @Override
    public NoteResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        String timestamp = rs.getString("noteUpdatedAt");
        LocalDateTime parsedDate;

        try {
            parsedDate = LocalDateTime.parse(timestamp, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        } catch (DateTimeParseException e1) {
            try {
                parsedDate = LocalDateTime.parse(timestamp, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS"));
            } catch (DateTimeParseException e2) {
                parsedDate = LocalDateTime.parse(timestamp, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
        }

        return NoteResponse.builder()
                .noteId(rs.getInt("noteId"))
                .noteTitle(rs.getString("noteTitle"))
                .noteContent(rs.getString("noteContent"))
                .noteUserId(rs.getInt("noteUserId"))
                .noteUpdatedAt(parsedDate)
                .build();
    }
}
