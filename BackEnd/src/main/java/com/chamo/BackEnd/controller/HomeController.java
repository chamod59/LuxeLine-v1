package com.chamo.BackEnd.controller;

import com.chamo.BackEnd.services.JWTService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    private final JWTService jwtService;

    public HomeController(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping("/username")
    public String getUsername(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return jwtService.getUserName(token);
    }

}
