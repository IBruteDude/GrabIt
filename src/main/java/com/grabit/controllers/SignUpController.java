package com.grabit.controllers;

import com.grabit.dtos.CustomerRequest;
import com.grabit.services.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/signup")
public class SignUpController {

   @Autowired
    private SignUpService signUpService;

    @PostMapping("/customer")
    public String signUpCustomer(@RequestBody CustomerRequest customerDTO) {

        try {
            signUpService.signUpCustomer(customerDTO);
        } catch (Exception e) {
            return "Error";

        }
        return "Success";
    }
}
