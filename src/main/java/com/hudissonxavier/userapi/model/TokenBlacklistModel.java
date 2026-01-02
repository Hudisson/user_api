package com.hudissonxavier.userapi.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade representando tokens JWT inválidos (logout)
 */
@Entity
@Table(name = "tb_token_blacklist")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenBlacklistModel {

    @Id
    @GeneratedValue
    private UUID id;

    // Token JWT invalidado no logout
    @Column(length = 500, nullable = false)
    private String token;
}
