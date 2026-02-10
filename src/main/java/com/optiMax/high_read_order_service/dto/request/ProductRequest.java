package com.optiMax.high_read_order_service.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    private String name;
    private int quantity;
}
