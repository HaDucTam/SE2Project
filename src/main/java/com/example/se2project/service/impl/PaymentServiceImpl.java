package com.example.se2project.service.impl;

import com.example.se2project.entity.Payment;
import com.example.se2project.entity.Product;
import com.example.se2project.repository.PaymentRepository;
import com.example.se2project.repository.ProductRepository;
import com.example.se2project.service.PaymentService;
import com.example.se2project.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl extends BaseServiceImpl<Payment, Long, PaymentRepository> implements PaymentService {
}