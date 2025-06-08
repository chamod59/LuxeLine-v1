package com.chamo.BackEnd.utill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardResponseDto {
    private int code;
    private String message;
    private Object data;
}
