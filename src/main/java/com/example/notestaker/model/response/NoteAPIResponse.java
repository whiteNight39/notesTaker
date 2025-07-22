package com.example.notestaker.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteAPIResponse<T> {

    private String noteAPIResponseCode;
    private String noteAPIResponseMessage;
    private T noteAPIResponseData;
}
