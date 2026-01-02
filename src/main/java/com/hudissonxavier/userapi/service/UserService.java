package com.hudissonxavier.userapi.service;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hudissonxavier.userapi.dto.UpdateUserRequestDTO;
import com.hudissonxavier.userapi.dto.UserRequestDTO;
import com.hudissonxavier.userapi.dto.UserResponseDTO;
import com.hudissonxavier.userapi.model.UserModel;
import com.hudissonxavier.userapi.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Serviço para gerenciar usuários (CRUD)
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;


    /**
     * Cria um novo usuário
     * Verifica se o e-mail já existe no banco antes de criar
     */
    public UserResponseDTO create(UserRequestDTO dto) {

        // Verifica se o e-mail já existe
        if (repository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já cadastrado");
        }

        // Criação do novo usuário
        UserModel user = UserModel.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .build();

        // Salva o novo usuário no banco
        repository.save(user);

        // Retorna a resposta com os dados do usuário
        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
    }



    public UserResponseDTO findById(UUID id) {
        UserModel user = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
    }

    public UserResponseDTO update(UUID id, UpdateUserRequestDTO dto) {
        UserModel user = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if (dto.getName() != null && !dto.getName().isBlank()) {
            user.setName(dto.getName());
        }

        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            repository.findByEmail(dto.getEmail()).ifPresent(existingUser -> {
                if (!existingUser.getId().equals(id)) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já em uso");
                }
            });
            user.setEmail(dto.getEmail());
        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(encoder.encode(dto.getPassword()));
        }

        repository.save(user);
        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
        repository.deleteById(id);
    }
}
