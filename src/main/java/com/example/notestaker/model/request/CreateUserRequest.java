package com.example.notestaker.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    @NotBlank(message = "User name is required")
    private String userName;

    @NotBlank(message = "User Email is required")
    @Email(message = "Invalid Email format")
    private String userEmail;

    @NotBlank(message = "User Password is required")
    private String userPassword;
}