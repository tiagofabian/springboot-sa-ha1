package com.springboot_sa_ha1.collections.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "colecciones")
@Getter
@Setter

public class Collection {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  private String name;

  @NotBlank
  private String email;


  private String phone;

  @NotBlank
  private String password;

}



