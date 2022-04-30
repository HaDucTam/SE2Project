package com.example.se2project.repository;

import com.example.se2project.entity.Order;
import com.example.se2project.entity.OrderDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends BaseRepository<OrderDetail, Long>{
    List<OrderDetail> findOrderDetailByOrder(Order order);
}
