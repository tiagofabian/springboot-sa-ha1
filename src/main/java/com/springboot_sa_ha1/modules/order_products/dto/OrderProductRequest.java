package com.springboot_sa_ha1.modules.order_products.dto;

public record OrderProductRequest (
  Long productId,
  Long orderId,
  Integer quantity,
  Integer price   //calcular esto de forma automática desde la bd y quitarlo de aquí
){}