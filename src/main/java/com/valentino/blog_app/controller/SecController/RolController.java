package com.valentino.blog_app.controller.SecController;

import com.valentino.blog_app.dto.UpdateRoleDTO;
import com.valentino.blog_app.model.Permission;
import com.valentino.blog_app.model.Role;
import com.valentino.blog_app.service.secService.IPermissionService;
import com.valentino.blog_app.service.secService.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPermissionService permissionService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getRoles")
    public ResponseEntity<List<Role>> getRoles() {
        try {
            List<Role> role = roleService.findAllRoles();
            return ResponseEntity.ok(role);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getRole/{id}")
    public ResponseEntity<Role> getRole(@PathVariable Long id) {
        Optional<Role> role = roleService.findRoleById(id);
        return role.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN'))")
    @PostMapping("/crear-role")
    public ResponseEntity<Role> crearRole(@RequestBody Role role) {
        try {
            Set<Permission> permisions = new HashSet<>();
            Permission readPermision;
            for(Permission per : role.getPermissionList()) {
                readPermision = permissionService.findPermissionById(per.getId())
                        .orElseThrow(() -> new RuntimeException("No se encuntra ese permiso"));
                if (readPermision != null) {
                    permisions.add(readPermision);
                }
            }
                role.setPermissionList(permisions);
                Role newRole = roleService.saveRole(role);
                return ResponseEntity.ok(newRole);
            }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/editRole/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Long id,
                                           @RequestBody UpdateRoleDTO roleDTO) {
        try {
            Role updateRole = roleService.updateRole(id,roleDTO);
            return ResponseEntity.ok(updateRole);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }




}
