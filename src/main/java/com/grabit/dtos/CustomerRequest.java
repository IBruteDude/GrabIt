package com.grabit.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CustomerRequest {

    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;


}
