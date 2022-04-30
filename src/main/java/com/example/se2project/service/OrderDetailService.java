package com.example.se2project.service;

import com.example.se2project.entity.Order;
import com.example.se2project.entity.OrderDetail;

import java.util.List;


public interface OrderDetailService extends BaseService<OrderDetail, Long>{
    List<OrderDetail> findOrderDetailByOrder(Order order);
}
