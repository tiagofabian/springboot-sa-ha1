package com.springboot_sa_ha1.modules.collections.repository;

import com.springboot_sa_ha1.modules.collections.model.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {
  Optional<Collection> findByName(String name);

  List<Collection> findBySlugIn(List<String> slugs);

  @Query("""
     SELECT DISTINCT c
     FROM Collection c
     LEFT JOIN FETCH c.productCollections pc
     LEFT JOIN FETCH pc.product p
     LEFT JOIN FETCH p.images
     LEFT JOIN FETCH p.category
     WHERE c.slug IN :slugs
  """)
  List<Collection> findAllBySlugsWithProducts(
      @Param("slugs") List<String> slugs
  );
}
