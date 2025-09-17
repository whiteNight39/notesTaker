package com.example.notestaker.controller;

import com.example.notestaker.model.request.CreateUserRequest;
import com.example.notestaker.model.request.UpdateUserPasswordRequest;
import com.example.notestaker.model.request.UserSignInRequest;
import com.example.notestaker.model.response.UserBasicResponse;
import com.example.notestaker.model.response.UserResponse;
import com.example.notestaker.model.response.UserAPIResponse;
import com.example.notestaker.model.response.UserSignInResponse;
import com.example.notestaker.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserAPIResponse<String>> createUser(@Valid @RequestBody CreateUserRequest request,
                                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );

            UserAPIResponse<String> errorResponse = UserAPIResponse.<String>builder()
                    .userAPIResponseCode("400")
                    .userAPIResponseMessage("Validation failed")
                    .userAPIResponseData(errors.toString())
                    .build();

            return ResponseEntity.badRequest().body(errorResponse);
        }

        try {
            userService.createUser(request);

            UserAPIResponse<String> successResponse = UserAPIResponse.<String>builder()
                    .userAPIResponseCode("200")
                    .userAPIResponseMessage("User created successfully")
                    .build();

            return ResponseEntity.ok(successResponse);
        } catch (Exception e) {
            UserAPIResponse<String> errorResponse = UserAPIResponse.<String>builder()
                    .userAPIResponseCode("500")
                    .userAPIResponseMessage("ERROR")
                    .userAPIResponseData(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<UserAPIResponse<Object>> signInUser(@Valid @RequestBody UserSignInRequest request,
                                                              BindingResult bindingResult, HttpServletRequest servletRequest) {

        String ip = servletRequest.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = servletRequest.getRemoteAddr();
        } else {
            ip = ip.split(",")[0];  // Use the first IP if multiple are present
        }
        String userAgent = servletRequest.getHeader("User-Agent");

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );

            UserAPIResponse<Object> errorResponse = UserAPIResponse.<Object>builder()
                    .userAPIResponseCode("400")
                    .userAPIResponseMessage("Validation failed")
                    .userAPIResponseData(errors)
                    .build();

            return ResponseEntity.badRequest().body(errorResponse);
        }
        try {
            UserBasicResponse response = userService.signInUser(request.getUserEmail(), request.getUserPassword(), ip, userAgent);
//            response.setUserPassword(null);

            UserAPIResponse<Object> successResponse = UserAPIResponse.<Object>builder()
                    .userAPIResponseCode("200")
                    .userAPIResponseMessage("User signed in successfully")
                    .userAPIResponseData(response)
                    .build();

            return ResponseEntity.ok(successResponse);
        } catch (Exception e) {
            UserAPIResponse<Object> errorResponse = UserAPIResponse.<Object>builder()
                    .userAPIResponseCode("500")
                    .userAPIResponseMessage("ERROR")
                    .userAPIResponseData(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<UserAPIResponse<String>> updateUser(@RequestParam String userName) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            int userId;

            try {
                userId = Integer.parseInt(principal.toString());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid user ID in token");
            }

            userService.updateUser(userName, userId);

            UserAPIResponse<String> response = UserAPIResponse.<String>builder()
                    .userAPIResponseCode("200")
                    .userAPIResponseMessage("User updated successfully")
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            UserAPIResponse<String> errorResponse = UserAPIResponse.<String>builder()
                    .userAPIResponseCode("500")
                    .userAPIResponseMessage("ERROR")
                    .userAPIResponseData(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PatchMapping("/update-password")
    public ResponseEntity<UserAPIResponse<String>> updateUserPassword(@Valid @RequestBody UpdateUserPasswordRequest request) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            int userId;

            try {
                userId = Integer.parseInt(principal.toString());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid user ID in token");
            }

            userService.updateUserPassword(userId, request.getUserOldPassword(), request.getUserNewPassword());

            UserAPIResponse<String> response = UserAPIResponse.<String>builder()
                    .userAPIResponseCode("200")
                    .userAPIResponseMessage("Password updated successfully")
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            UserAPIResponse<String> errorResponse = UserAPIResponse.<String>builder()
                    .userAPIResponseCode("500")
                    .userAPIResponseMessage("ERROR")
                    .userAPIResponseData(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/get-user")
    public ResponseEntity<UserAPIResponse<Object>> getUserById() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            int userId;

            try {
                userId = Integer.parseInt(principal.toString());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid user ID in token");
            }

            UserBasicResponse user = userService.getUserById(userId);

            UserAPIResponse<Object> response = UserAPIResponse.<Object>builder()
                    .userAPIResponseCode("200")
                    .userAPIResponseMessage("User retrieved successfully")
                    .userAPIResponseData(user)
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            UserAPIResponse<Object> errorResponse = UserAPIResponse.<Object>builder()
                    .userAPIResponseCode("500")
                    .userAPIResponseMessage("ERROR")
                    .userAPIResponseData("ERROR: "+e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<UserAPIResponse<String>> deleteUser() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            int userId;

            try {
                userId = Integer.parseInt(principal.toString());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid user ID in token");
            }

            userService.deleteUser(userId);

            UserAPIResponse<String> response = UserAPIResponse.<String>builder()
                    .userAPIResponseCode("200")
                    .userAPIResponseMessage("User deleted successfully")
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            UserAPIResponse<String> errorResponse = UserAPIResponse.<String>builder()
                    .userAPIResponseCode("500")
                    .userAPIResponseMessage("ERROR")
                    .userAPIResponseData(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
