package com.springboot_sa_ha1.security.service;

import com.springboot_sa_ha1.modules.customers.model.Customer;
import com.springboot_sa_ha1.modules.customers.repository.CustomerRepository;
import com.springboot_sa_ha1.security.model.MainCustomer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomersDetailsService implements UserDetailsService {

    private final CustomerRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer u = repository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return new MainCustomer(u);
    }
}
