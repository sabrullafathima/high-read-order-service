package com.optiMax.high_read_order_service.exception;

public class resourceNotFoundException extends RuntimeException {

    public resourceNotFoundException(String msg, long id) {
        super(msg + id);
    }
}
