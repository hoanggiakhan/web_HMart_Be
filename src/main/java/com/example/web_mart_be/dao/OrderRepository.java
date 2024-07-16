package com.example.web_mart_be.dao;

import com.example.web_mart_be.entity.Order;
import com.example.web_mart_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "orders")
public interface OrderRepository extends JpaRepository<Order,Integer> {
    public Order findFirstByUserOrderByIdOrderDesc(User user);
}
