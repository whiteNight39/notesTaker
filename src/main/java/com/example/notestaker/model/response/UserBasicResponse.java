package com.example.notestaker.model.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBasicResponse {

    private Integer userId;
    private String userName;
    private String userEmail;
    private String userAuthJwtToken;
}
