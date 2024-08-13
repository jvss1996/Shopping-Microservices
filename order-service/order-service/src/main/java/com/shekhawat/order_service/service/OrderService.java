package com.shekhawat.order_service.service;

import com.shekhawat.order_service.client.InventoryClient;
import com.shekhawat.order_service.dto.OrderRequest;
import com.shekhawat.order_service.model.Order;
import com.shekhawat.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    public void placeOrder(OrderRequest orderRequest) {
        var isInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if (isInStock) {
            // map OrderRequest to Order object
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());
            // save Order to order repository
            orderRepository.save(order);
            log.info("Order placed successfully");
        } else {
            throw new RuntimeException("Product with skuCode " + orderRequest.skuCode() + " is out of stock");
        }

    }
}
