package com.chamo.BackEnd.controller;

import com.chamo.BackEnd.services.JWTService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    private final JWTService jwtService;

    public HomeController(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping("/username")
    public String getUsername(@RequestParam String token){
        return jwtService.getUserName(token);
    }
}
