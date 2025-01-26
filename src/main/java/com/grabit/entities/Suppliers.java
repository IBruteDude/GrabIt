package com.grabit.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.PrivateKey;

@Table(name="suppliers")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Suppliers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name="name")
    private String name;

    @Column(name="address")
    private String address;

    @Column(name="phone")
    private String phone;

    @Email
    @Column(name="email", unique = true)
    private String email;

    @Column(name="national_id", unique = true)
    private String nationalId;

    @NotBlank
    @Column(name="city")
    private String city;

}
