package com.example.se2project.service.impl;

import com.example.se2project.entity.Category;
import com.example.se2project.entity.Order;
import com.example.se2project.entity.User;
import com.example.se2project.repository.CategoryRepository;
import com.example.se2project.repository.OrderRepository;
import com.example.se2project.service.CategoryService;
import com.example.se2project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, Long, OrderRepository> implements OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Override
    public List<Order> getOrderByUser(User user) {
        List<Order> order = orderRepository.getOrderByUser(user);
        return order;
    }
}
