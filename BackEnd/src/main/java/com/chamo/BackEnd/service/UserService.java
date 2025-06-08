package com.chamo.BackEnd.service;

import com.chamo.BackEnd.dto.request.UserRequestDto;
import com.chamo.BackEnd.dto.response.UserResponseDto;

import java.util.List;

public interface UserService {
    void createUser(UserRequestDto userRequestDto);
    List<UserResponseDto> getAllUsers();
}
