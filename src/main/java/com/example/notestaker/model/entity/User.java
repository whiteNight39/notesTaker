package com.example.notestaker.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer userId;
    private String userName;
    private String userEmail;
    private String userPassword;

    private String userStatus;
    private LocalDateTime userCreatedAt;
    private LocalDateTime userUpdatedAt;
}
