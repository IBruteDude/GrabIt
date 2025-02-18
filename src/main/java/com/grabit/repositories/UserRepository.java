package com.grabit.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grabit.entities.User;


public interface UserRepository extends JpaRepository<User, UUID> {
}
