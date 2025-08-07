package com.valentino.blog_app.service.secService;

import com.valentino.blog_app.dto.UpdateRoleDTO;
import com.valentino.blog_app.model.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {

    List<Role> findAllRoles();

    Optional<Role> findRoleById(Long id);

    Role saveRole(Role role);

    void deleteRoleById(Long id);

    Role updateRole(Long id, UpdateRoleDTO role);

}
