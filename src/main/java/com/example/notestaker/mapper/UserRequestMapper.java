package com.example.notestaker.mapper;

import com.example.notestaker.model.entity.User;
import com.example.notestaker.model.request.CreateUserRequest;
import com.example.notestaker.model.request.UpdateUserRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRequestMapper {
    User toEntity(CreateUserRequest createUserRequest);
    User toEntity(UpdateUserRequest updateUserRequest);
}
