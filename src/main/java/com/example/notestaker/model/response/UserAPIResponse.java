package com.example.notestaker.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAPIResponse<T> {

    private String userAPIResponseCode;
    private String userAPIResponseMessage;
    private T userAPIResponseData;
}
