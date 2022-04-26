package com.example.se2project.repository;

import com.example.se2project.entity.Category;
import com.example.se2project.entity.Order;
import com.example.se2project.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends BaseRepository<Order, Long>{
    List<Order> getOrderByUser(User user);
}
