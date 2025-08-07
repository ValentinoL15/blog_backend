package com.valentino.blog_app.repository;

import com.valentino.blog_app.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRespository extends JpaRepository<Role,Long> {

    Optional<Role> findByRole(String name);
}
