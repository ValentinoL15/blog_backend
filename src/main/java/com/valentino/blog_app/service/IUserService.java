package com.valentino.blog_app.service;

import com.valentino.blog_app.dto.CreateUsersDTO;
import com.valentino.blog_app.dto.UserDTO;
import com.valentino.blog_app.model.UserSec;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<UserDTO> getAllUsers();

    Optional<UserSec> getUserById(Long id);

    UserSec saveUser(UserSec user);

    CreateUsersDTO createUser(CreateUsersDTO usersDTO);

    void deleteUserById(Long id);

    UserSec updateUser(Long id, UserSec user);

    public String encryptPassword(String password);

}
