package com.example.notestaker.repository.database.interfaces;

import com.example.notestaker.model.entity.UserAuth;

public interface UserAuthRepository {

    void createUserAuth(UserAuth userAuth);
    UserAuth getUserAuth(Integer userId, String ip, String userAgent);
}
