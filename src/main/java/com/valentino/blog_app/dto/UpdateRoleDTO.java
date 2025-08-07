package com.valentino.blog_app.dto;

import com.valentino.blog_app.model.Permission;

import java.util.Set;

public record UpdateRoleDTO(String Role,
                            Set<Permission> permissionList) {
}
