package com.openclassrooms.mddapi.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.DTO.DBUserDTO;
import com.openclassrooms.mddapi.services.DBUserService;

/**
 * REST controller for managing user-related operations.
 */
@RestController
@RequestMapping("/api")
public class DBUserController {

  @Autowired
  private DBUserService DBUserService;

  /**
   * Get a user by their ID.
   *
   * @param id The ID of the user to retrieve.
   * @return ResponseEntity containing the user data if found, or a 404 status if not found.
   */
  @GetMapping(value = "/user/{id}", produces = "application/json")
  public ResponseEntity<Optional<DBUserDTO>> getUserById(@PathVariable Long id) {

    // Récupération des informations de l'utilisateur
    Optional<DBUserDTO> dBUserDTO = DBUserService.getUserById(id);

    if (dBUserDTO == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    return ResponseEntity.ok(dBUserDTO);
  }

  /**
   * Get the currently authenticated user.
   *
   * @return ResponseEntity containing the user data if authenticated, or a 401 status if not authenticated.
   */
  @GetMapping("/auth/me")
  public ResponseEntity<Optional<DBUserDTO>> getCurrentUser() {

    // Récupération de l'utilisateur actuellement connecté
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      return ResponseEntity.status(401).build();
    }

    String email = authentication.getName();

    Optional<DBUserDTO> user = DBUserService.getUserByEmail(email);

    if (user == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(user);
  }

  /**
   * Updates a user's information.
   *
   * @param id The ID of the user to update.
   * @param userDTO The updated user data.
   * @return ResponseEntity containing the updated user data if found, or a 404 status if not found.
   */
  @PutMapping("/user/{id}")
  public ResponseEntity<Optional<DBUserDTO>> updateUser(@PathVariable Long id, @RequestBody DBUserDTO userDTO) {
    Optional<DBUserDTO> updatedUser = DBUserService.updateUser(id, userDTO);
    if (updatedUser == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.ok(updatedUser);
  }

}
