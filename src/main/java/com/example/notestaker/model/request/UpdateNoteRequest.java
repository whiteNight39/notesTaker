package com.example.notestaker.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNoteRequest {

    private Integer noteId;
    private String noteTitle;
    private String noteContent;
//    @NotNull(message = "Note User ID is required to update note")
//    private Integer noteUserId;

    private String noteStatus;
}
