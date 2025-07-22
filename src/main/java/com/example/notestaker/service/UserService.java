package com.example.notestaker.service;

import com.example.notestaker.mapper.UserRequestMapper;
import com.example.notestaker.model.entity.User;
import com.example.notestaker.model.entity.UserAuth;
import com.example.notestaker.model.request.CreateUserRequest;
import com.example.notestaker.model.response.UserBasicResponse;
import com.example.notestaker.model.response.UserResponse;
import com.example.notestaker.model.response.UserSignInResponse;
import com.example.notestaker.repository.database.interfaces.UserAuthRepository;
import com.example.notestaker.repository.database.interfaces.UserRepository;
import com.example.notestaker.utils.validator.JwtUtil;
import com.example.notestaker.utils.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserAuthRepository userAuthRepository;
    private final UserRequestMapper userRequestMapper;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository, UserAuthRepository userAuthRepository, UserRequestMapper userRequestMapper, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.userAuthRepository = userAuthRepository;
        this.userRequestMapper = userRequestMapper;
        this.jwtUtil = jwtUtil;
    }

    public void createUser(CreateUserRequest request) {
        if (request == null) throw new IllegalArgumentException("User request cannot be null");

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(request.getUserPassword());

        User user = userRequestMapper.toEntity(request);
        user.setUserPassword(hashedPassword);

        userRepository.createUser(user);
    }

    public UserBasicResponse signInUser(String userEmail, String userPassword, String  ip, String userAgent) {
        if (userEmail == null || userEmail.isEmpty() || userPassword == null || userPassword.isEmpty()) {
            throw new IllegalArgumentException("User email or password cannot be null or empty");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        List<UserSignInResponse> responseList = userRepository.getUserByEmail(userEmail);
        if (responseList == null || responseList.isEmpty()) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        for (UserSignInResponse response : responseList) {
            String storedHashedPassword = response.getUserPassword();

            if (encoder.matches(userPassword, storedHashedPassword)) {

                UserAuth auth = userAuthRepository.getUserAuth(response.getUserId(), ip, userAgent);
                String token;

                if (auth == null) {
                    token = jwtUtil.generateToken(response.getUserId());
                    UserAuth newAuth = UserAuth.builder()
                            .authUserId(jwtUtil.extractUserId(token))
                            .authJwtToken(token)
                            .authIssuedAt(jwtUtil.getIssueDate(token))
                            .authExpiresAt(jwtUtil.getExpirationDate(token))
                            .authIsValid(jwtUtil.isTokenValid(token))
                            .authUserDeviceIp(ip)
                            .authUserDeviceAgent(userAgent)
                            .build();

                    userAuthRepository.createUserAuth(newAuth);
                } else {
                    token = auth.getAuthJwtToken();
                }

                return UserBasicResponse.builder()
                        .userAuthJwtToken(token)
                        .build();
            }
        }

        throw new IllegalArgumentException("Invalid email or password");
    }

    public void updateUser(String userName, Integer userId) {
        if (userId == null) throw new IllegalArgumentException("User id is required");

        UserResponse user = userRepository.getUserById(userId);
        UserValidator.validate(user);

        userRepository.updateUser(userName, userId);
    }

    public void updateUserPassword(Integer userId, String userOldPassword, String userNewPassword) {
        if  (userId == null) throw new IllegalArgumentException("User id is required");

        UserResponse user = userRepository.getUserById(userId);
        UserValidator.validate(user);

        String storedHashedPassword  = user.getUserPassword();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(userOldPassword, storedHashedPassword )) throw new IllegalArgumentException("Old password doesn't match");

        String hashedNewPassword = encoder.encode(userNewPassword);
        userRepository.updateUserPassword(userId, hashedNewPassword);
    }

    public UserBasicResponse getUserById(Integer userId) {
        if (userId == null) throw new IllegalArgumentException("User id is required");

        UserResponse user = userRepository.getUserById(userId);
        UserValidator.validate(user);

        return UserBasicResponse.builder()
                .userName(user.getUserName())
                .userEmail(user.getUserEmail())
                .build();
    }

    public void deleteUser(Integer userId) {
        if (userId == null) throw new IllegalArgumentException("User id is required");

        UserResponse user = userRepository.getUserById(userId);
        UserValidator.validate(user);

        userRepository.deleteUser(userId);
    }
}
