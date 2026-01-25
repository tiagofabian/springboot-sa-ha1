package com.springboot_sa_ha1.modules.product_collections.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCollectionId implements Serializable {

  @Column(name = "product_id")
  private Long productId;

  @Column(name = "collection_id")
  private Long collectionId;
}