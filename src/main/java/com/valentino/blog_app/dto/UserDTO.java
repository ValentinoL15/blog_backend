package com.valentino.blog_app.dto;

import com.valentino.blog_app.model.Role;

import java.util.Set;

public record UserDTO(String username, Set<Role> role) {

}
