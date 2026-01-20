package com.springboot_sa_ha1.security.model;

import com.springboot_sa_ha1.modules.customers.model.RolCustomer;
import com.springboot_sa_ha1.modules.customers.model.Customer;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record MainCustomer(Customer customer) implements UserDetails {

    @Override
    @NonNull
    public Collection<? extends GrantedAuthority> getAuthorities() {
        RolCustomer rol = customer.getRol();
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol.name()));
    }

    @Override
    @NonNull
    public String getPassword() { return customer.getPasswordHash(); }

    @Override
    @NonNull
    public String getUsername() { return customer.getEmail(); }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return customer.isActive(); }

}
