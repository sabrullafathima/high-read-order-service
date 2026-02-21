package com.optiMax.high_read_order_service.service;

import com.optiMax.high_read_order_service.dto.request.OrderRequest;
import com.optiMax.high_read_order_service.dto.response.CursorPageOrderResponse;
import com.optiMax.high_read_order_service.dto.response.OrderResponse;
import com.optiMax.high_read_order_service.entity.Order;
import com.optiMax.high_read_order_service.entity.Product;
import com.optiMax.high_read_order_service.exception.resourceNotFoundException;
import com.optiMax.high_read_order_service.repository.OrderRepository;
import com.optiMax.high_read_order_service.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

    public OrderResponse createOrder(OrderRequest request) {
        int retries = 3;

        while (retries > 0) {
            try {
                return createOrderWithRetry(request);
            } catch (ObjectOptimisticLockingFailureException e) {
                retries--;
                if (retries == 0) {
                    throw new RuntimeException("Too much concurrent traffic. Try again.");
                }
            }
        }
        throw new RuntimeException("Unexpected error");
    }

    @Transactional
    public OrderResponse createOrderWithRetry(OrderRequest orderRequest) {
        Product product = productService.findProductById(orderRequest);

        productService.isProductAvailable(product);

        product.setAvailableQuantity(product.getAvailableQuantity() - 1);
        productRepository.save(product);
        
        Order order = buildOrder(orderRequest, product);
        orderRepository.save(order);

        return buildOrderResponseWithProductQuantity(order, product);
    }

    private Order buildOrder(OrderRequest orderRequest, Product product) {
        return Order.builder()
                .customerName(orderRequest.getCustomerName())
                .amount(orderRequest.getAmount())
                .productId(product.getId())
                .build();
    }

    private OrderResponse buildOrderResponseWithProductQuantity(Order order, Product product) {
        return OrderResponse.builder()
                .id(order.getId())
                .customerName(order.getCustomerName())
                .amount(order.getAmount())
                .productId(order.getProductId())
                .availableQuantity(product.getAvailableQuantity())
                .build();
    }

    private OrderResponse buildOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .customerName(order.getCustomerName())
                .amount(order.getAmount())
                .productId(order.getProductId())
                .build();
    }

    public OrderResponse getOrdersById(long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new resourceNotFoundException("Order Not Found | orderId: ", id));
        return buildOrderResponse(order);
    }

    public CursorPageOrderResponse<OrderResponse> getOrders(Long lastId, int size) {
        int limit = size + 1;
        List<Order> orders;

        if (lastId == null) {
            orders = orderRepository.findFirstPage(limit);
        } else {
            orders = orderRepository.findNextPage(lastId, limit);
        }

        boolean hasNext = orders.size() > size;

        if (hasNext) {
            orders = orders.subList(0, size);
        }

        List<OrderResponse> data = buildData(orders);

        Long nextCursor = findNextCursor(data);

        return new CursorPageOrderResponse<>(data, nextCursor, hasNext);

    }

    private List<OrderResponse> buildData(List<Order> orders) {
        return orders.stream()
                .map(this::buildOrderResponse)
                .toList();
    }

    private Long findNextCursor(List<OrderResponse> data) {
        return data.isEmpty()
                ? null
                : data.getLast().getId();
    }
}
