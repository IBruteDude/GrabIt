package com.grabit.services;

import com.grabit.dtos.LogInRequest;
import com.grabit.entities.User;
import com.grabit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class LogInService {

    @Autowired
    private UserRepository userRepo;
    public String login(LogInRequest logInRequest){

        try {
          User user= userRepo.findByEmailAndPassword(logInRequest.getEmail(), logInRequest.getPassword());
          return "Login Successful";
        }
        catch (Exception e){
            throw new RuntimeException("Invalid email or password");
        }





    }
}
