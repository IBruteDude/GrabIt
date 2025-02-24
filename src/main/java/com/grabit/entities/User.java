package com.grabit.entities;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "users", indexes = @Index(name = "ix_username", columnList = "username", unique = true))
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    @Column(name = "username", length = 50, nullable = false, unique = true)
    protected String username;

    @Column(name = "username", insertable = false, updatable = false)
    protected String email;

    @Column(name = "password", length = 500, nullable = false)
    protected String password;

    @Column(name = "phone", length = 20, nullable = false)
    private String phone;

    @Column(name = "enabled", nullable = false)
    protected boolean enabled;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    protected Set<Authority> authorities = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    protected Profile profile;

}
