package com.hudissonxavier.userapi.dto;

import lombok.Data;

/**
 * DTO para criação de usuário (registro)
 */
@Data
public class UserRequestDTO {
    private String name;
    private String email;
    private String password;
}
