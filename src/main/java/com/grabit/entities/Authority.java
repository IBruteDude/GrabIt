package com.grabit.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.UUID;

@Value
class UserAuthorityId implements Serializable {
    private UUID id;
    private User user;
    private String authority;
}

@Entity
@Table(
    name = "authorities",
    indexes = {@Index(name = "ix_auth_username", columnList = "username, authority", unique = true)},
    uniqueConstraints= {@UniqueConstraint(name = "unique_authority", columnNames = {"username", "authority"})}
)
@Getter
@Setter
@ToString(exclude = "user")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@IdClass(UserAuthorityId.class)
public class Authority extends BaseEntity {

    @Id
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false),
        @JoinColumn(name = "user_username", referencedColumnName = "username", nullable = false)
    })
    private User user;

    @Id
    @Column(name = "authority", length = 50, nullable = false)
    private String authority;

}
