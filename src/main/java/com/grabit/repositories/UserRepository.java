package com.grabit.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grabit.entities.User;


public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);
    User findByUsername(String username);
    User findByEmailAndPassword(String email, String password);

}
