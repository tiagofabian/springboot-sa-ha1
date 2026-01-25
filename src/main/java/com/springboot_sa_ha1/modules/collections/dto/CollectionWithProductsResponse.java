
package com.springboot_sa_ha1.modules.collections.dto;
import com.springboot_sa_ha1.modules.products.dto.ProductResponse;
import java.util.List;

public record CollectionWithProductsResponse(
    Long id,
    String name,
    String description,
    String slug,
    String image,
    List<ProductResponse> products
) {}
