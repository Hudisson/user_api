package com.hudissonxavier.userapi.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO para resposta de usuário
 */
@Data
@AllArgsConstructor
public class UserResponseDTO {
    private UUID id;
    private String name;
    private String email;
}
