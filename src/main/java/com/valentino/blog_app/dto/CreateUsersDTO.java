package com.valentino.blog_app.dto;

import com.valentino.blog_app.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record CreateUsersDTO(@NotBlank String username,
                             @NotBlank String email,
                             @NotBlank String password,
                             @NotEmpty Set<Role> rolesList) {
}
