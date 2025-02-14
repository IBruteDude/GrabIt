package com.grabit.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import java.io.Serializable;

@Data
class UserAuthorityId implements Serializable {
    private User user;
    private String authority;
}

 @Entity
@Table(
    name = "authorities",
    indexes = {@Index(name = "ix_auth_username", columnList = "username, authority", unique = true)},
    uniqueConstraints= {@UniqueConstraint(name = "unique_authority", columnNames = {"username", "authority"})}
)
@Data
@IdClass(UserAuthorityId.class)
public class Authority {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
    private User user;

    @Id
    @Column(name = "authority", length = 50, nullable = false)
    private String authority;

}
