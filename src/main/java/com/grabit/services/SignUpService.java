package com.grabit.services;

import com.grabit.dtos.CustomerRequest;
import com.grabit.entities.User;
import com.grabit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SignUpService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void signUpCustomer(CustomerRequest customerRequest) {
        User user = User.builder()
                .username(customerRequest.getName())
                .email(customerRequest.getEmail())
                .phone(customerRequest.getPhone())
                 .password(passwordEncoder.encode(customerRequest.getPassword()))
                .enabled(true)
                .build();

        userRepository.save(user);
    }
}