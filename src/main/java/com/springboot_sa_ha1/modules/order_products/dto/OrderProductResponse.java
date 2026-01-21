package com.springboot_sa_ha1.modules.order_products.dto;

import com.springboot_sa_ha1.modules.order_products.model.OrderProductId;

public record OrderProductResponse (
  OrderProductId id,
  Integer quantity,
  Integer price
){}
