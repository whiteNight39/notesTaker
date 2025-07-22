package com.example.notestaker.utils.validator;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "";
        String hashed = encoder.encode(rawPassword);
        System.out.println("Hashed password: " + hashed);
    }
}
