package com.optiMax.high_read_order_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderRequest {
    private String customerName;
    private BigDecimal amount;
}
