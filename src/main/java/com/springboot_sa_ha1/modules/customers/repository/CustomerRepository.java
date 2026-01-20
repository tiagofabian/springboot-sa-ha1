package com.springboot_sa_ha1.modules.customers.repository;

import com.springboot_sa_ha1.modules.customers.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
  Optional<Customer> findByEmailIgnoreCase(String email);
  boolean existsByEmailIgnoreCase(String email);
}
