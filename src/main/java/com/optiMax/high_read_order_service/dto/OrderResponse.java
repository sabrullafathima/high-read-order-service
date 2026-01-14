package com.optiMax.high_read_order_service.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class OrderResponse {
    private Long id;
    private String customerName;
    private BigDecimal amount;
}
