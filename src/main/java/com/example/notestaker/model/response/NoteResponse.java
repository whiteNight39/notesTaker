package com.example.notestaker.model.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteResponse {

    private Integer noteId;
    private String noteTitle;
    private String noteContent;
    private Integer noteUserId;
    private LocalDateTime noteUpdatedAt;
}
