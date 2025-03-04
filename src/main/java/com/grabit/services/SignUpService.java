package com.grabit.services;

import com.grabit.dtos.CustomerRequest;
import com.grabit.entities.User;
import com.grabit.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignUpService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String signUpCustomer(CustomerRequest customerRequest) {
        User user = User.builder()
                .username(customerRequest.getName())
                .email(customerRequest.getEmail())
                .password(passwordEncoder.encode(customerRequest.getPassword()))
                .enabled(true)
                .build();

        var createdUser = userRepository.save(user);
        return "Created user with id " + createdUser.getId() + " successfully";
    }
}
