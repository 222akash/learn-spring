package com.learnspring.orderservice.service;

import com.learnspring.orderservice.model.OrderRequest;

public interface OrderService {
    Long placeOrder(OrderRequest orderRequest);
}
