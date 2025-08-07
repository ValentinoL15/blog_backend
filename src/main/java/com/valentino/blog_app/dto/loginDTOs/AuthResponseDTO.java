package com.valentino.blog_app.dto.loginDTOs;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.valentino.blog_app.model.Role;

import java.util.Set;

@JsonPropertyOrder({"username", "message", "jwt", "status"})
public record AuthResponseDTO(String username,
                              String message,
                              String jwt,
                              Set<Role> roles,
                              boolean status) {
}
