package com.grabit.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(
    name = "authorities",
    indexes = @Index(name = "ix_auth_username", columnList = "user_id, authority", unique = true),
    uniqueConstraints= @UniqueConstraint(name = "unique_authority", columnNames = {"user_id", "authority"})
)
@Getter
@Setter
@ToString(exclude = "user")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true, exclude = "user")
public class Authority extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "authority", length = 50, nullable = false)
    private String authority;

    public static Authority of(User user, String authority) {
        return new Authority(user, authority);
    }

}
