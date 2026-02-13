package com.takeout.controller;

import com.takeout.model.FoodOrder;
import com.takeout.repository.FoodOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class FoodOrderController {

    @Autowired
    private FoodOrderRepository orderRepository;

    @GetMapping("/user/{userId}")
    public List<FoodOrder> getUserOrders(@PathVariable Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @PostMapping
    public FoodOrder createOrder(@RequestBody FoodOrder order) {
        order.setStatus("PENDING");
        return orderRepository.save(order);
    }
}
