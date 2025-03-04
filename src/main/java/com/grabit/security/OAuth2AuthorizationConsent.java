package com.grabit.security;

import jakarta.persistence.*;

@Entity
@Table(name = "oauth2_authorization_consent")
public class OAuth2AuthorizationConsent {

    @Id
    @ManyToOne
    @JoinColumn(name = "registered_client_id", nullable = false)
    private OAuth2RegisteredClient registeredClient;

    @Id
    @Column(name = "principal_name", length = 200, nullable = false)
    private String principalName;

    @Column(name = "authorities", length = 1000, nullable = false)
    private String authorities;

}
