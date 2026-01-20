package com.springboot_sa_ha1.modules.customers.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_customer")
  private Long id;

  @NotBlank
  private String name;

  @NotBlank
  private String email;


  private String phone;

  @Column(name = "password_Hash", nullable = false, length = 100)
  private String passwordHash;

  @Column(name = "active", nullable = false)
  private boolean active = true;

  @Enumerated(EnumType.STRING)
  @Column(name = "rol", nullable = false)
  private RolCustomer rol = RolCustomer.ADMIN;
}