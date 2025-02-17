package com.grabit.entities;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;




@Value
class CategoryId implements Serializable {
    private UUID id;
}

@Entity
@Table(name = "categories")
@Getter
@Setter
@ToString(exclude = "parentCategory")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@IdClass(CategoryId.class)
public class Category extends BaseEntity {

	@Column(name = "name", length = 50, nullable = false)
	private String name;

	@Column(name = "brief", length = 200, nullable = true)
	private String brief;

	@Column(name = "description", length = 500, nullable = true)
	private String description;

	@Column(name = "icon_image", length = 200, nullable = true)
	private String iconImage;


	@ManyToOne
	private Category parentCategory;

	@OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Category> subcategories;

}
