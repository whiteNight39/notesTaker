package com.example.notestaker.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNoteRequest {

    private String noteTitle;
    private String noteContent;

//    @NotNull(message = "Note User is required")
//    private Integer noteUserId;
}
