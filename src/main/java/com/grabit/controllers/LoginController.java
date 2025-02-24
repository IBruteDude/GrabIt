package com.grabit.controllers;

import com.grabit.dtos.LogInRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {


    @GetMapping("/login")
	public String login(@RequestBody LogInRequest logInRequest) {

        return "Login Successful";


    }
}
