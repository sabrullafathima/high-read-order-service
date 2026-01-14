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
//        if (orderRepository.count() > 0) return;

        //Delete all existing rows
        orderRepository.deleteAll();
        System.out.println("Deleted existing orders...");

        //Insert 1M rows in batches
        List<Order> batch = new ArrayList<>();
        int total = 1_000_000;

        for (int i = 1; i <= total; i++) {
            batch.add(Order.builder()
                    .customerName("Customer-" + i)
                    .amount(BigDecimal.valueOf(i * 10))
                    .build());

            if (i % 1000 == 0) { // insert in batches of 1000
                orderRepository.saveAll(batch);
                batch.clear();

                // Print progress every 10k rows instead of every batch
                if (i % 10000 == 0) {
                    System.out.println("Inserted " + i + " orders...");
                }
            }
        }
        System.out.println("Seeding completed: 1M orders inserted.");
    }
}
