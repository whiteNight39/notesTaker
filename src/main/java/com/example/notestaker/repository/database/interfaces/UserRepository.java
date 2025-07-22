package com.example.notestaker.repository.database.interfaces;

import com.example.notestaker.model.entity.User;
import com.example.notestaker.model.response.UserResponse;
import com.example.notestaker.model.response.UserSignInResponse;

import java.util.List;

public interface UserRepository {

    void createUser(User user);
    List<UserSignInResponse> getUserByEmail(String userEmail);
    void updateUser(String userName, int userId);
    void updateUserPassword(int userId, String userNewPassword);
    UserResponse getUserById(int userId);
    void deleteUser(int userId);
}