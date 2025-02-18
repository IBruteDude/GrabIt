package com.grabit.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@SuperBuilder
public class BaseEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	protected UUID id;

	@CreatedDate
	@Column(name = "created_at", nullable = false)
	protected Instant createdAt;

	@LastModifiedDate
	@Column(name = "updated_at", nullable = false)
	protected Instant updatedAt;

}
