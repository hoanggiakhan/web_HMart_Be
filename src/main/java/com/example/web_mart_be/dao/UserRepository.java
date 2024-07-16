package com.example.web_mart_be.dao;

import com.example.web_mart_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "users")
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByUsername(String username);
    public User findByEmail(String email);
    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);
}
