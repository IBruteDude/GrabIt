package com.grabit.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "products")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseEntity {

	@Column(name = "name", length = 50, unique = true, nullable = false)
	String name;

	@Column(name = "description", length = 500, unique = true, nullable = true)
	String description;


	@ManyToOne
	@JoinColumn(name = "brand_id", nullable = false)
	private Brand brand;

	@ManyToOne
	@JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
	private Category category;

    @ManyToOne
	@JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

}
