package com.optiMax.high_read_order_service.controller;

import com.optiMax.high_read_order_service.dto.OrderRequest;
import com.optiMax.high_read_order_service.dto.OrderResponse;
import com.optiMax.high_read_order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        OrderResponse response = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderResponse> getOrdersById(@PathVariable long id) {
        OrderResponse response = orderService.getOrdersById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<OrderResponse> response = orderService.getOrders(page, size);
        return ResponseEntity.ok(response);
    }
}
