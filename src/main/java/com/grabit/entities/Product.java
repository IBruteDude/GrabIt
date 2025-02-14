package com.grabit.entities;

import jakarta.persistence.*;
import lombok.*;


@Table (name="products")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {

    @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     @Column(name="name")
    private String name;

    @Column(name="price")
    private double price;

    @Column(name="discount")
    private double discount;  // by percentage

    @Column(name="units_in_order")
    private int unitsInOrder;

    @Column(name="units_in_stock")
    private int unitsInStock;

   @JoinColumn(
            name = "category_id",
            referencedColumnName = "id",
            nullable = false
    )
    @ManyToOne(fetch = FetchType.LAZY)
    private Categories category;

    @JoinColumn(
            name = "supplier_id",
            referencedColumnName = "id",
            nullable = false
    )
    @ManyToOne(fetch = FetchType.LAZY)
    private Suppliers supplier;




}
