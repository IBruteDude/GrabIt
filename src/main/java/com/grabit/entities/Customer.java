package com.grabit.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import  com.grabit.entities.User;
import  com.grabit.entities.Order;

import java.util.Set;

@Entity
@Table(name = "customers")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Customer extends User {

    @Column(name = "national_id", unique = true, nullable = false)
    private String nationalId;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Order> orders;



}
