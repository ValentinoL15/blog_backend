package com.valentino.blog_app.service.secService;

import com.valentino.blog_app.model.Permission;

import java.util.List;
import java.util.Optional;

public interface IPermissionService {

    List<Permission> findAllPermissions();

    Optional<Permission> findPermissionById(Long id);

    Permission savePermission(Permission permission);

    void deletePermissionById (Long id);

    Permission updatePermission(Long id, Permission permission);

}
