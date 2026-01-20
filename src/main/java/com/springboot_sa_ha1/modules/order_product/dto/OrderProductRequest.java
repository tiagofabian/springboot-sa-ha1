package com.springboot_sa_ha1.modules.order_product.dto;

import com.springboot_sa_ha1.modules.order_product.model.OrderProductId;

public record OrderProductRequest (
  Long productId,
  Long orderId,
  Integer quantity,
  Integer price   //calcular esto de forma automática desde la bd y quitarlo de aquí
){}