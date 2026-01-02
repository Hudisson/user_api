package com.hudissonxavier.userapi.dto;

import java.util.UUID;
import lombok.Data;

/**
 * DTO para atualizar usuário
 */
@Data
public class UpdateUserRequestDTO {
    private UUID id; // ID do usuário a ser atualizado
    private String name;
    private String email;
    private String password; // Opcional
}
