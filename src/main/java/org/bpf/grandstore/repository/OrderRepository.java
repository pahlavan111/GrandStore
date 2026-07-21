package org.bpf.grandstore.repository;


import org.bpf.grandstore.entity.Order;
import org.bpf.grandstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
            select distinct o
            from Order o
            left join fetch o.items
            where o.customer = :customer
            """)
    List<Order> findAllByCustomer(User customer);
}
