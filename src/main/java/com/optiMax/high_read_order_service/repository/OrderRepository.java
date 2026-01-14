package com.optiMax.high_read_order_service.repository;

import com.optiMax.high_read_order_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
