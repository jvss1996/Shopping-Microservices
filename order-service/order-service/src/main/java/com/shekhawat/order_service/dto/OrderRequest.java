package com.shekhawat.order_service.dto;

import java.math.BigDecimal;

public record OrderRequest(Long id, String orderNumber, String skuCode, BigDecimal price, Integer quantity, UserDetails userDetails) {

    public record UserDetails(String firstName, String lastName, String email) {}
}
