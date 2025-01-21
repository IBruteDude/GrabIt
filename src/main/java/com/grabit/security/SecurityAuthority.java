package com.grabit.security;

import org.springframework.security.core.GrantedAuthority;
import com.grabit.entities.Authority;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SecurityAuthority implements GrantedAuthority {

    private Authority authority;

    @Override
    public String getAuthority() {
        return authority.getAuthority();
    }

}
