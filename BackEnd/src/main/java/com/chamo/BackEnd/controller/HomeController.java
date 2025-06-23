package com.chamo.BackEnd.controller;

import com.chamo.BackEnd.services.JWTService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class HomeController {
    private final JWTService jwtService;

    public HomeController(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping
    public String getHello(){
        return "hi";
    }

    @PostMapping("/login")
    public String login(){
        return null;
    }

    @GetMapping("/username")
    public String getUsername(@RequestParam String token){
        return jwtService.getUserName(token);
    }
}
