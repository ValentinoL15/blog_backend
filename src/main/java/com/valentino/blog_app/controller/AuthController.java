package com.valentino.blog_app.controller;

import com.valentino.blog_app.dto.CreateUsersDTO;
import com.valentino.blog_app.dto.UserDTO;
import com.valentino.blog_app.dto.loginDTOs.AuthResponseDTO;
import com.valentino.blog_app.dto.loginDTOs.LoginDTO;
import com.valentino.blog_app.model.UserSec;
import com.valentino.blog_app.service.IUserService;
import com.valentino.blog_app.service.UserDetailsServiceImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private IUserService userService;

    @Autowired
    private UserDetailsServiceImp userDetailsService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody CreateUsersDTO userDTO) {
        try {
            CreateUsersDTO usuario = userService.createUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Ocurrió un error inesperado"));
        }
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO login) {
        try {
            AuthResponseDTO response = userDetailsService.loginUser(login);
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Usuario no encontrado"));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Contraseña incorrecta"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error interno"));
        }
    }

}
