package com.springboot_sa_ha1.modules.order_products.mapper;

import com.springboot_sa_ha1.modules.order_products.dto.OrderProductResponse;
import com.springboot_sa_ha1.modules.order_products.model.OrderProduct;
import org.springframework.stereotype.Component;

@Component
public class OrderProductMapper {
  public OrderProductResponse toResponse(OrderProduct orderProduct) {
    return new OrderProductResponse(
        orderProduct.getId(),
        orderProduct.getQuantity(),
        orderProduct.getPrice()
    );
  }
}