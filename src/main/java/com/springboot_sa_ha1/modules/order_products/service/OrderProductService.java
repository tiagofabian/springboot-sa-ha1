package com.springboot_sa_ha1.modules.order_products.service;

import com.springboot_sa_ha1.modules.order_products.dto.OrderProductRequest;
import com.springboot_sa_ha1.modules.order_products.dto.OrderProductResponse;
import com.springboot_sa_ha1.modules.order_products.model.OrderProductId;

import java.util.List;

public interface OrderProductService {
  List<OrderProductResponse> listarTodos();
  OrderProductResponse obtenerPorId(OrderProductId opid);
  OrderProductResponse guardar(OrderProductRequest request);
  OrderProductResponse actualizar(OrderProductId opid, OrderProductRequest request);
  void eliminar(OrderProductId opid);
}