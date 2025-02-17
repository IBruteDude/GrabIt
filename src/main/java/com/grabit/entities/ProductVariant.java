package com.grabit.entities;

import com.grabit.constants.Size;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "product_variants")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ProductVariant extends BaseEntity {

	@Column(name = "price", nullable = false)
	private double price;

	@Column(name = "stock_quantity", nullable = false)
	private int stockQuantity;

	@Column(name = "color", nullable = true, length = 7)
	private String color;

	@Column(name = "weight", nullable = true)
	private double weight;

	@Enumerated
	private Size size;


	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

}
