package com.example.notestaker.model.request;

import jakarta.validation.constraints.Email;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignInRequest {

    @Email(message = "Invalid email format")
    private String userEmail;
    private String userPassword;
}
