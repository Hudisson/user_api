package com.hudissonxavier.userapi.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    
    private String email;
    private String password;
}
