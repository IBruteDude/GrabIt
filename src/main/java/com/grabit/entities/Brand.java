package com.grabit.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "brands")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Brand extends BaseEntity {

	@Column(name = "name", length = 50, nullable = false)
	private String name;

	@Column(name = "logo_image", length = 200, nullable = true)
	private String logoImage;

	@Column(name = "description", length = 50, nullable = true)
	private String description;

}
