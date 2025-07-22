package com.example.notestaker.model.request;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    private Integer userId;
    private String userName;
//    @Email(message = "Invalid Email format")
//    private String userEmail;
    private String userPassword;

    private String userStatus;
}
