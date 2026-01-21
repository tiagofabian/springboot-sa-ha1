package com.springboot_sa_ha1.modules.order_products.repository;

import com.springboot_sa_ha1.modules.order_products.model.OrderProduct;
import com.springboot_sa_ha1.modules.order_products.model.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository
    extends JpaRepository<OrderProduct, OrderProductId> {  // ¡Objeto como ID!

}