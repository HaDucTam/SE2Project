package com.example.se2project.service;

import com.example.se2project.entity.Order;
import com.example.se2project.entity.Product;
import com.example.se2project.entity.User;

import java.util.List;

public interface OrderService extends BaseService<Order, Long>{
    List<Order> getOrderByUser(User user);
}
