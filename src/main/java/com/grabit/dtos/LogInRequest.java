package com.grabit.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LogInRequest {
    private String email;
    private String password;


}
