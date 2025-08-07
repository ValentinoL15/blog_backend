package com.valentino.blog_app.dto.loginDTOs;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(@NotBlank String username,
                       @NotBlank String password) {
}
