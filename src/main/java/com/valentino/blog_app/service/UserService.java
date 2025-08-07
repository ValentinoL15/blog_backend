package com.valentino.blog_app.service;

import com.valentino.blog_app.dto.CreateUsersDTO;
import com.valentino.blog_app.dto.UserDTO;
import com.valentino.blog_app.model.Autor;
import com.valentino.blog_app.model.Lector;
import com.valentino.blog_app.model.Role;
import com.valentino.blog_app.model.UserSec;
import com.valentino.blog_app.repository.IAutorRepository;
import com.valentino.blog_app.repository.ILectorRepository;
import com.valentino.blog_app.repository.IRoleRespository;
import com.valentino.blog_app.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService{

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRespository roleRespository;

    @Autowired
    private IAutorRepository autorRepository;

    @Autowired
    private ILectorRepository lectorRepository;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDTO(user.getUsername(),user.getRolesList()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserSec> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserSec saveUser(UserSec user) {
        return userRepository.save(user);
    }

    @Override
    public CreateUsersDTO createUser(CreateUsersDTO usersDTO) {
        UserSec user = null;

        List<UserSec> users = userRepository.findAll();

        Set<Role> validateRole = new HashSet<>();
        for (Role rol: usersDTO.rolesList()) {
            Role foundRole = roleRespository.findById(rol.getId())
                    .orElseThrow(() -> new RuntimeException("No se encuentra ese rol"));
            validateRole.add(foundRole);

            if(foundRole.getRole().equals("AUTOR")){
                user = new Autor();
                break;
            }
            if(foundRole.getRole().equals("LECTOR")){
                user = new Lector();
                break;
            }
            if(foundRole.getRole().equals("ADMIN")){
                user = new UserSec();
                break;
            }

        }

        if (user == null) {
            throw new RuntimeException("Rol inválido o no especificado");
        }

        user.setUsername(usersDTO.username());
        user.setPassword(encryptPassword(usersDTO.password()));
        user.setRolesList(validateRole);

        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new RuntimeException("Debe ingresar un username");
        }

        if(user.getUsername() != null) {
            for(UserSec usuario: users) {
                if(user.getUsername().toUpperCase().equals(usuario.getUsername().toUpperCase())){
                    throw new RuntimeException("El username ya está en uso, por favor utilice otro");
                }
            }
        }

        if(user.getUsername().trim().isEmpty() || user.getUsername() == null ) {
            throw new RuntimeException("El username no puede estar vacio");
        }

        if(user.getUsername().length() <= 3) {
            throw new RuntimeException("El username debe contener más de 3 caracteres");
        }

        if(user.getPassword() == null) {
            throw new RuntimeException("Debe ingresar una contraseña");
        }


        userRepository.save(user);
        return usersDTO;
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserSec updateUser(Long id, UserSec user) {
        UserSec existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if(user.getUsername() != null) user.setUsername(user.getUsername());
        if(user.getRolesList() != null){
            Set<Role> rolesList = new HashSet<>();
            for(Role rol : user.getRolesList()) {
                Role attached = roleRespository.findById(rol.getId())
                        .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
                    rolesList.add(attached);
            }
            existingUser.setRolesList(rolesList);
        }
        return userRepository.save(existingUser);
    }

    @Override
    public String encryptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
