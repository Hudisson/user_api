package com.hudissonxavier.userapi.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class DeleteUserRequestDTO {
    
    private UUID id; // ID do usuário a ser deletado
}


