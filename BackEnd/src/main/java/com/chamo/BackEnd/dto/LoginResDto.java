package com.chamo.BackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResDto {
    private String token;
    private LocalDateTime loginTime;
    private String error;
    private String message;
}
