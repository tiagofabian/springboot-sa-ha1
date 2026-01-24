package com.springboot_sa_ha1.modules.collections.repository;

import com.springboot_sa_ha1.modules.collections.model.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
  Optional<Collection> findByName(String name);
}
