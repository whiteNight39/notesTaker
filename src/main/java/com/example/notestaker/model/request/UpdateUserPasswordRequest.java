package com.example.notestaker.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserPasswordRequest {

    @NotBlank(message = "User Old Password is required")
    private String userOldPassword;

    @NotBlank(message = "User Old Password is required")
    private String userNewPassword;
}
