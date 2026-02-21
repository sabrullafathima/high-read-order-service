package com.optiMax.high_read_order_service.repository;

import com.optiMax.high_read_order_service.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("""
            select o from Order o
            where (:lastId is null or o.id > :lastId)
            order by o.id
            """)
    List<Order> findNextPage(@Param("lastId") Long lastId, Pageable pageable);

    @Query(value = "SELECT * FROM `orders` ORDER BY id ASC LIMIT :limit", nativeQuery = true)
    List<Order> findFirstPage(@Param("limit") int limit);

    @Query(value = "SELECT * FROM `orders` WHERE id > :lastId ORDER BY id ASC LIMIT :limit", nativeQuery = true)
    List<Order> findNextPage(@Param("lastId") Long lastId,
                             @Param("limit") int limit);
}
