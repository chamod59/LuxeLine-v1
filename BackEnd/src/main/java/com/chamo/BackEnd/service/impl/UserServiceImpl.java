package com.chamo.BackEnd.service.impl;

import com.chamo.BackEnd.dto.request.UserRequestDto;
import com.chamo.BackEnd.dto.response.UserResponseDto;
import com.chamo.BackEnd.entity.UserEntity;
import com.chamo.BackEnd.repository.UserRepository;
import com.chamo.BackEnd.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void createUser(UserRequestDto userRequestDto) {
        userRepository.save(toUser(userRequestDto));

        UserEntity user = toUser(userRequestDto);
        System.out.println("Creating user: " + user);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toUserResponseDto)
                .toList();
    }


    private UserEntity toUser(UserRequestDto userRequestDto) {
        return UserEntity.builder()
                .name(userRequestDto.getName())
                .email(userRequestDto.getEmail())
                .password(userRequestDto.getPassword())
                .username(userRequestDto.getUsername())
                .build();

    }

    private UserResponseDto toUserResponseDto(UserEntity user) {
        if (user == null) {
            return null;
        }
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .username(user.getUsername())
                .build();
    }


}
