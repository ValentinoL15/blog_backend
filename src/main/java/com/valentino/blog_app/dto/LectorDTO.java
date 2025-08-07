package com.valentino.blog_app.dto;

import com.valentino.blog_app.model.Role;

import java.util.Set;

public record LectorDTO(String username, Set<Role> roleslist){
}
