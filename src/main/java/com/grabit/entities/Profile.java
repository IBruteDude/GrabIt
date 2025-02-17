package com.grabit.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@ToString(exclude = "user")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Profile extends BaseEntity {

	@Column(name = "first_name", length = 50, nullable = true)
	private String firstName;

	@Column(name = "last_name", length = 50, nullable = true)
	private String lastName;

	@Column(name = "country", length = 20, nullable = true)
	private String country;

	@Column(name = "state", length = 50, nullable = true)
	private String state;

	@Column(name = "city", length = 50, nullable = true)
	private String city;

	@Column(name = "zip_code", length = 5, nullable = true)
	private String zipCode;

	@Column(name = "address", length = 100, nullable = true)
	private String address;

	@Column(name = "phone_number", length = 20, nullable = true)
	private String phoneNumber;


    @OneToOne
	@JoinColumns({
		@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false),
		@JoinColumn(name = "user_username", referencedColumnName = "username", nullable = false),
	})
    private User user;

}
