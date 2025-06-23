package com.chamo.BackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterReqDto {
    private String name;
    private String email;
    private String username;
    private String password;
}
