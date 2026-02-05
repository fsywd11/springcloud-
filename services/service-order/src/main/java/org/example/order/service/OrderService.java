package org.example.order.service;

import org.example.order.bean.Order;

public interface OrderService {
    Order createOrder(Long userId, Long productId);
}
