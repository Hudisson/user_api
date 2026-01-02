package com.hudissonxavier.userapi.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hudissonxavier.userapi.dto.LoginRequestDTO;
import com.hudissonxavier.userapi.dto.LoginResponseDTO;
import com.hudissonxavier.userapi.model.TokenBlacklistModel;
import com.hudissonxavier.userapi.model.UserModel;
import com.hudissonxavier.userapi.repository.TokenBlacklistRepository;
import com.hudissonxavier.userapi.repository.UserRepository;
import com.hudissonxavier.userapi.security.JwtService;

import lombok.RequiredArgsConstructor;

/**
 * Serviço de autenticação (login/logout)
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final TokenBlacklistRepository blacklistRepository;
    private final PasswordEncoder encoder;

    public LoginResponseDTO login(LoginRequestDTO dto) {

        UserModel user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não encontrado"));

        if (!encoder.matches(dto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Senha inválida");
        }

        String token = jwtService.generateToken(user.getId(), user.getEmail(), user.getName());
        return new LoginResponseDTO(token);
    }

    public void logout(String token) {
        blacklistRepository.save(TokenBlacklistModel.builder().token(token).build());
    }
}
