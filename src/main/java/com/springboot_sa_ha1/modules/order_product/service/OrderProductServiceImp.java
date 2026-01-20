package com.springboot_sa_ha1.modules.order_product.service;

import com.springboot_sa_ha1.modules.order_product.dto.OrderProductRequest;
import com.springboot_sa_ha1.modules.order_product.dto.OrderProductResponse;
import com.springboot_sa_ha1.modules.order_product.mapper.OrderProductMapper;
import com.springboot_sa_ha1.modules.order_product.model.OrderProduct;
import com.springboot_sa_ha1.modules.order_product.model.OrderProductId;
import com.springboot_sa_ha1.modules.order_product.repository.OrderProductRepository;
import com.springboot_sa_ha1.modules.order_product.service.OrderProductService;
import com.springboot_sa_ha1.modules.orders.model.Order;
import com.springboot_sa_ha1.modules.orders.repository.OrderRepository;
import com.springboot_sa_ha1.modules.products.model.Product;
import com.springboot_sa_ha1.modules.products.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderProductServiceImp implements OrderProductService {
  //private final OrderProductRepository orderProductRepository;

  private final OrderProductRepository repository;
  private final OrderProductMapper mapper;
  private final ProductRepository productRepository;    // ← NUEVO
  private final OrderRepository orderRepository;

  public OrderProductServiceImp(OrderProductRepository repository, ProductRepository productRepository, OrderRepository orderRepository, OrderProductMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
    this.orderRepository = orderRepository;
    this.productRepository= productRepository;
  }

  @Override
  public List<OrderProductResponse> listarTodos(){
    return repository.findAll().stream()
        .map(mapper::toResponse)
        .collect(Collectors.toList());
  }

  @Override
  public OrderProductResponse obtenerPorId(OrderProductId id){
    return repository.findById(id)
        .map(mapper::toResponse)
        .orElseThrow(() -> new RuntimeException("Dirección no encontrado"));
  }

  @Override
  public OrderProductResponse guardar(OrderProductRequest request){

    Order order = orderRepository.findById(request.orderId())
        .orElseThrow(() -> new RuntimeException("Order no existe"));

    Product product = productRepository.findById(request.productId())
        .orElseThrow(() -> new RuntimeException("Product no existe"));

    OrderProductId id = new OrderProductId(request.productId(), request.orderId());
    OrderProduct orderProduct = new OrderProduct();
    orderProduct.setId(id);
    orderProduct.setOrder(order);      // 🔑
    orderProduct.setProduct(product);  // 🔑
    orderProduct.setQuantity(request.quantity());
    orderProduct.setPrice(request.price());

    return mapper.toResponse(repository.save(orderProduct));
  }

  @Override
  public OrderProductResponse actualizar(OrderProductId id, OrderProductRequest request){
    OrderProduct orderProduct = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Dirección no encontrada"));
    orderProduct.setQuantity(request.quantity());
    orderProduct.setPrice(request.price());
    return mapper.toResponse(repository.save(orderProduct));
  }

  @Override
  public void eliminar(OrderProductId id){
    repository.deleteById(id);
  }
}
