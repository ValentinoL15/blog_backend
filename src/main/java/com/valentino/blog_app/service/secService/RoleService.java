package com.valentino.blog_app.service.secService;

import com.valentino.blog_app.dto.UpdateRoleDTO;
import com.valentino.blog_app.model.Permission;
import com.valentino.blog_app.model.Role;
import com.valentino.blog_app.repository.IRoleRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleService implements IRoleService{

    @Autowired
    private IRoleRespository roleRespository;

    @Autowired
    private IPermissionService permissionService;

    @Override
    public List<Role> findAllRoles() {
        return roleRespository.findAll();
    }

    @Override
    public Optional<Role> findRoleById(Long id) {
        return roleRespository.findById(id);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRespository.save(role);
    }

    @Override
    public void deleteRoleById(Long id) {
        roleRespository.deleteById(id);
    }

    @Override
    public Role updateRole(Long id, UpdateRoleDTO roleDTO) {
        Role rol = roleRespository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el rol"));

        if(roleDTO.Role() != null) {
            rol.setRole(roleDTO.Role());
        }

        if(roleDTO.permissionList() != null) {
            Set<Permission> permissionList = new HashSet<>();
            for(Permission per: roleDTO.permissionList()){
                Permission permission = permissionService.findPermissionById(per.getId())
                        .orElseThrow(() -> new RuntimeException("No se encuentra el permiso"));
                permissionList.add(permission);
            }
            rol.setPermissionList(permissionList);
        }

        return roleRespository.save(rol);

    }
}
