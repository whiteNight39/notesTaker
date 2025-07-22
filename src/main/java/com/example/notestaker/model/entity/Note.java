package com.example.notestaker.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Note {

    private Integer noteId;
    private String noteTitle;
    private String noteContent;
    private Integer noteUserId;

    private String noteStatus;
    private LocalDateTime noteCratedAt;
    private LocalDateTime noteUpdatedAt;
}
