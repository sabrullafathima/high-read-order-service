package com.optiMax.high_read_order_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CursorPageOrderResponse<T> {
    private List<T> data;
    private Long nextCursor;
    private boolean hasNext;
}
