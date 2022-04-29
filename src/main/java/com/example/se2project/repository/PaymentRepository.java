package com.example.se2project.repository;

import com.example.se2project.entity.CartProduct;
import com.example.se2project.entity.Payment;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends BaseRepository<Payment, Long>{
}
