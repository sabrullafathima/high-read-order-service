package com.optiMax.high_read_order_service.service;

import com.optiMax.high_read_order_service.dto.OrderRequest;
import com.optiMax.high_read_order_service.dto.OrderResponse;
import com.optiMax.high_read_order_service.entity.Order;
import com.optiMax.high_read_order_service.exception.resourceNotFoundException;
import com.optiMax.high_read_order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderResponse createOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
                .customerName(orderRequest.getCustomerName())
                .amount(orderRequest.getAmount())
                .build();
        orderRepository.save(order);
        return buildOrderResponse(order);
    }

    private OrderResponse buildOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .customerName(order.getCustomerName())
                .amount(order.getAmount())
                .build();
    }

    public OrderResponse getOrdersById(long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new resourceNotFoundException("Order Not Found | orderId: ", id));
        return buildOrderResponse(order);
    }

    public List<OrderResponse> getOrders(Long lastId, int size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by("id"));
        return orderRepository.findNextPage(lastId, pageable)
                .stream()
                .map(this::buildOrderResponse)
                .toList();
    }
}
