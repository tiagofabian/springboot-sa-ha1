package com.springboot_sa_ha1.modules.orders.dto;
import java.time.LocalDate;

public record OrderResponse(
        Long id,
        LocalDate orderDate,
        Long total,
        Integer customerId
) {}
