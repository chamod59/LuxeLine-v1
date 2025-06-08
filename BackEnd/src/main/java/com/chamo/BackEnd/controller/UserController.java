package com.chamo.BackEnd.controller;

import com.chamo.BackEnd.dto.request.UserRequestDto;
import com.chamo.BackEnd.service.UserService;
import com.chamo.BackEnd.utill.StandardResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<StandardResponseDto> createUser(@RequestBody UserRequestDto userRequestDto) {
        userService.createUser(userRequestDto);
        return new ResponseEntity<>(new StandardResponseDto(201, "User created successfully", null), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<StandardResponseDto> getAllUsers() {
        return new ResponseEntity<>(new StandardResponseDto(200, "All users", userService.getAllUsers()), HttpStatus.OK);
    }

}
