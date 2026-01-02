
package com.hudissonxavier.userapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hudissonxavier.userapi.dto.LoginRequestDTO;
import com.hudissonxavier.userapi.dto.LoginResponseDTO;
import com.hudissonxavier.userapi.dto.UserRequestDTO;
import com.hudissonxavier.userapi.dto.UserResponseDTO;
import com.hudissonxavier.userapi.service.AuthService;
import com.hudissonxavier.userapi.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller de autenticação (login/register/logout)
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequestDTO dto) {

        try {
            // Tenta criar o usuário

            UserResponseDTO response = userService.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);


        }catch (ResponseStatusException ex){

            // Se o e-mail já existe, retorna conflito (409) com um JSON simples

            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.CONFLICT.value());
            response.put("message", "O e-mail informado já está em uso");


            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);

        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO dto) {

        try {

            // Tenta fazer o login e gerar o token
            LoginResponseDTO response = authService.login(dto);
            return ResponseEntity.ok(response);

        }catch (ResponseStatusException ex){

            // Caso as credenciais estejam incorretas, retorna um JSON simples com status e mensagem
            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.UNAUTHORIZED.value());
            response.put("message", "E-mail ou senha inválidos");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }


    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String header) {
        authService.logout(header.substring(7));
        return ResponseEntity.noContent().build();
    }
}
