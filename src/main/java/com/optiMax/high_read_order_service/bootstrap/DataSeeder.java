package com.optiMax.high_read_order_service.bootstrap;

import com.optiMax.high_read_order_service.entity.Order;
import com.optiMax.high_read_order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final OrderRepository orderRepository;

    @Override
    public void run(String... args) {
        if (orderRepository.count() > 0) return;

        List<Order> batch = new ArrayList<>();

        for (int i = 1; i <= 100_000; i++) {
            batch.add(Order.builder()
                    .customerName("Customer-" + i)
                    .amount(BigDecimal.valueOf(i * 10))
                    .build());

            if (i % 1000 == 0) {
                orderRepository.saveAll(batch);
                batch.clear();
            }
        }
    }
}
