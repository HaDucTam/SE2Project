package com.example.se2project.service.impl;

import com.example.se2project.entity.Order;
import com.example.se2project.entity.OrderDetail;
import com.example.se2project.repository.OrderDetailRepository;
import com.example.se2project.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl extends BaseServiceImpl<OrderDetail, Long, OrderDetailRepository> implements OrderDetailService {
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Override
    public List<OrderDetail> findOrderDetailByOrder(Order order) {
        return orderDetailRepository.findOrderDetailByOrder(order);
    }
}
