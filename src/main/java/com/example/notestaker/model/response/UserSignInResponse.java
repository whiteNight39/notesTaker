package com.example.notestaker.model.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignInResponse {

    private Integer userId;
    private String userName;
    private String userEmail;
    private String userPassword;
}
