package com.chamo.BackEnd.controller;

import com.chamo.BackEnd.dto.auth.LoginReqDto;
import com.chamo.BackEnd.dto.auth.LoginResDto;
import com.chamo.BackEnd.dto.auth.RegisterReqDto;
import com.chamo.BackEnd.dto.auth.RegisterResDto;
import com.chamo.BackEnd.entity.UserEntity;
import com.chamo.BackEnd.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    private final AuthService authService;

    @GetMapping
    public List<UserEntity> getAllUsers(){
        return authService.getAllUsers();
    }

    @PostMapping
    public UserEntity createUser(@RequestBody RegisterReqDto user){
        return authService.createUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResDto> login(@RequestBody LoginReqDto loginData){
        LoginResDto res = authService.login(loginData);
        if(res.getError()!= null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResDto> register(@RequestBody RegisterReqDto registerData){
        RegisterResDto res = authService.register(registerData);
        if(res.getError()!= null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}