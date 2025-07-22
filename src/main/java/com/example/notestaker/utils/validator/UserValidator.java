package com.example.notestaker.utils.validator;

import com.example.notestaker.model.response.UserResponse;

public class UserValidator {

    public static void validate(UserResponse user) {
        if  (user == null) {
            throw new IllegalArgumentException("User not found");
        }
    }
}