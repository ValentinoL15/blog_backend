package com.valentino.blog_app.dto;

import com.valentino.blog_app.model.Role;

import java.util.Set;

public record AutorDTO(Long user_id,String username, Set<BlogDTO> blogsList, Set<Role> rolesList) {}

