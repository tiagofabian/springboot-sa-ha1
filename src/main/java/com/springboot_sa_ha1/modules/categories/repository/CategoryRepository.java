package com.springboot_sa_ha1.modules.categories.repository;

import com.springboot_sa_ha1.modules.categories.model.Category;
import com.springboot_sa_ha1.modules.products.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long >{
  @Query("""
    SELECT p
    FROM Product p
    WHERE p.category.slug = :slug
   """)
  List<Product> findByCategorySlug(String slug);
}
