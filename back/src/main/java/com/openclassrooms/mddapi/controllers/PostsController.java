package com.openclassrooms.mddapi.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.mddapi.DTO.CommentDTO;
import com.openclassrooms.mddapi.DTO.PostDTO;
import com.openclassrooms.mddapi.models.DBUser;
import com.openclassrooms.mddapi.repository.DBUserRepository;
import com.openclassrooms.mddapi.services.DBUserService;
import com.openclassrooms.mddapi.services.PostService;


/**
 * Controller for managing posts-related operations.
 */
@RestController
@RequestMapping("/api/posts")
public class PostsController {

  @Autowired
  private PostService postService;

  @Autowired
  private DBUserRepository dbUserRepository;

  /**
   * Retrieves a list of posts for a specific user.
   *
   * @param userId The ID of the user whose posts are to be retrieved.
   * @return A list of posts for the specified user.
   */
  @GetMapping
  public List<PostDTO> getUsersFeed(@RequestParam Long userId) {
      return postService.getPostsByUserId(userId);
  }

  /**
   * Creates a new post.
   *
   * @param postDTO The post data to be created.
   * @return ResponseEntity containing the created post data.
   */

  @PostMapping
  public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
    PostDTO createdPost = postService.createPost(postDTO);
    return ResponseEntity.ok(createdPost);
  }

  /**
   * Get a post by its ID.
   *
   * @param postId The ID of the post to retrieve.
   * @return ResponseEntity containing the post data if found.
   */
  @GetMapping("/{postId}")
  public ResponseEntity<?> getPostById(@PathVariable Long postId) {
      PostDTO postDTO = postService.getPostById(postId);

      // Convertir l'objet DTO en Map
      ObjectMapper objectMapper = new ObjectMapper();
      Map<String, Object> postMap = objectMapper.convertValue(postDTO, new TypeReference<Map<String, Object>>() {});

      // Ajouter topicName dans la map
      String topicName = postService.getTopicNameById(postDTO.getTopicId());
      postMap.put("topicName", topicName);

      return ResponseEntity.ok(postMap);
  }

  /**
   * Creates a new comment for a specific post.
   *
   * @param postId The ID of the post to comment on.
   * @param requestBody The request body containing the comment content.
   * @return ResponseEntity containing the created comment data.
   */
  @PostMapping("/{postId}/comments")
  public ResponseEntity<CommentDTO> createComment(
          @PathVariable Long postId,
          @RequestBody Map<String, String> requestBody) {

      // Récupération de l'utilisateur connecté
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      if (authentication == null || !authentication.isAuthenticated()) {
          return ResponseEntity.status(401).build();
      }

      // Récupérer l'email de l'utilisateur connecté
      String email = authentication.getName();

      // Trouver l'utilisateur en base
      Optional<DBUser> userOpt = dbUserRepository.findByEmail(email);
      if (userOpt.isEmpty()) {
          return ResponseEntity.status(404).body(null);
      }

      DBUser user = userOpt.get();

      // Récupération de l'ID utilisateur
      Long userId = user.getId();

      // Récupération du contenu du commentaire
      String content = requestBody.get("content");

      // Création du commentaire
      CommentDTO createdComment = postService.createComment(postId, content, userId);

      return ResponseEntity.ok(createdComment);
  }

}
