package com.grabit.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "authorities", indexes = {
        @Index(name = "ix_auth_username", columnList = "username, authority", unique = true)
})
@Data
public class Authority {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", referencedColumnName = "username", unique = true, nullable = false)
    private User user;

    @Column(name = "authority", length = 50, nullable = false)
    private String authority;

}
