package com.valentino.blog_app.controller.SecController;

import com.valentino.blog_app.model.Permission;
import com.valentino.blog_app.service.secService.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    @PostMapping("/create-permission")
    public ResponseEntity<Permission> createPermission(@RequestBody Permission permission) {
        try {
            Permission permission1 = permissionService.savePermission(permission);
            return ResponseEntity.ok(permission1);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
