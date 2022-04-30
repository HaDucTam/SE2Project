package com.example.se2project.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/my-order"})
public class UserOrderController {
    @GetMapping
    public String orderPage() {
        return "accountPages/orderList";
    }
}