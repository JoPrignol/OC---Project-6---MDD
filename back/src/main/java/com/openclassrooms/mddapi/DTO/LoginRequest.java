package com.openclassrooms.mddapi.DTO;

public class LoginRequest {
  // private String email;
  // private String username;

  private String identifier;

  private String password;



  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  // public String getEmail() {
  //   return email;
  // }

  // public void setEmail(String email) {
  //   this.email = email;
  // }


  // public String getUsername() {
  //   return username;
  // }

  // public void setUsername(String username) {
  //   this.username = username;
  // }
}
