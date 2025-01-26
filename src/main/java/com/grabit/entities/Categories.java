package com.grabit.entities;

import jakarta.persistence.*;
import lombok.*;


@Table(name="categories")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="image")
    private String image;
}
