package com.optiMax.high_read_order_service.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class OrderResponse {
    private Long id;
    private Long productId;
    private String customerName;
    private BigDecimal amount;
    private int availableQuantity;
}
