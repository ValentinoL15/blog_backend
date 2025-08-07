package com.valentino.blog_app.service.secService;

import com.valentino.blog_app.model.Permission;
import com.valentino.blog_app.repository.IPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService implements IPermissionService{

    @Autowired
    private IPermissionRepository permissionRepository;

    @Override
    public List<Permission> findAllPermissions() {
        return permissionRepository.findAll();
    }

    @Override
    public Optional<Permission> findPermissionById(Long id) {
        return permissionRepository.findById(id);
    }

    @Override
    public Permission savePermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    public void deletePermissionById(Long id) {
        permissionRepository.deleteById(id);
    }

    @Override
    public Permission updatePermission(Long id, Permission permission) {
        return null;
    }
}
