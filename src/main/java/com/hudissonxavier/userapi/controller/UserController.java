package com.hudissonxavier.userapi.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.hudissonxavier.userapi.dto.UpdateUserRequestDTO;
import com.hudissonxavier.userapi.dto.UserResponseDTO;
import com.hudissonxavier.userapi.model.UserModel;
import com.hudissonxavier.userapi.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * Controller para CRUD de usuários
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMyProfile(Authentication authentication) {
        UserModel user = (UserModel) authentication.getPrincipal();
        UserResponseDTO response = new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PutMapping("/edit")
    public ResponseEntity<UserResponseDTO> editUser(
            @RequestBody UpdateUserRequestDTO dto,
            Authentication authentication
    ) {
        UserModel currentUser = (UserModel) authentication.getPrincipal();

        if (!currentUser.getId().equals(dto.getId())) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        return ResponseEntity.ok(userService.update(dto.getId(), dto));
    }


    @DeleteMapping("/delete-me")
    public ResponseEntity<Void> deleteMyAccount(Authentication authentication) {
        UserModel currentUser = (UserModel) authentication.getPrincipal();
        userService.delete(currentUser.getId());
        return ResponseEntity.noContent().build();
    }
}
