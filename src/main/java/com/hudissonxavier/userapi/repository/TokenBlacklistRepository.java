package com.hudissonxavier.userapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hudissonxavier.userapi.model.TokenBlacklistModel;

@Repository
public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklistModel, UUID> {

    /**
     * Verifica se o token já está invalidado (logout)
     */
    boolean existsByToken(String token);
}
