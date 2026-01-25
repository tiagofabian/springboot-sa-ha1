package com.springboot_sa_ha1.modules.products.repository;

import com.springboot_sa_ha1.modules.products.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
  @Query("""
       SELECT DISTINCT p FROM Product p
       LEFT JOIN p.category c
       LEFT JOIN p.productCollections pc
       LEFT JOIN pc.collection col
       WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :term, '%'))
          OR LOWER(p.description) LIKE LOWER(CONCAT('%', :term, '%'))
          OR LOWER(c.name) LIKE LOWER(CONCAT('%', :term, '%'))
          OR LOWER(col.name) LIKE LOWER(CONCAT('%', :term, '%'))
       """)
  List<Product> searchByTerm(@Param("term") String term);

  // 🔹 Productos por categoría (slug)
  List<Product> findByCategorySlug(String slug);

  // 🔹 Productos por colección (slug)
  @Query("""
    SELECT DISTINCT p
    FROM Product p
    JOIN p.productCollections pc
    JOIN pc.collection c
    WHERE c.slug = :slug
  """)
  List<Product> findByCollectionSlug(@Param("slug") String slug);
}
