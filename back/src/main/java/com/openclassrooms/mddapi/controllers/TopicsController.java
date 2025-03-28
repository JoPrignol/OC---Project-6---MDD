package com.openclassrooms.mddapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.DTO.TopicDTO;
import com.openclassrooms.mddapi.services.TopicService;

/**
 * Controller for managing topic-related operations.
 */
@RestController
@RequestMapping("/api/topics")
public class TopicsController {

  @Autowired
  private TopicService topicService;

  /**
   * Retrieves a list of all topics.
   *
   * @return A list of all topics.
   */
  @GetMapping
  public List<TopicDTO> getAllTopics(){
    return topicService.getAllTopics();
  }

  /**
   * Retrieves a list of topics associated with a specific user.
   *
   * @param userId The ID of the user whose topics are to be retrieved.
   * @return A list of topics associated with the specified user.
   */
  @GetMapping("/user/{userId}")
  public List<TopicDTO> getTopicsByUserId(@PathVariable Long userId) {
    return topicService.getTopicsByUserId(userId);
  }
}
