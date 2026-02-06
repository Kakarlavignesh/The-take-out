package com.takeout.controller;

import com.takeout.model.FoodOrder;
import com.takeout.repository.FoodOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chef")
@CrossOrigin(origins = "*")
public class ChefController {

    @Autowired
    private FoodOrderRepository orderRepository;

    @GetMapping("/orders")
    public List<FoodOrder> getKitchenOrders() {
        // Return only active orders (Not Served)
        return orderRepository.findAll().stream()
                .filter(o -> !"SERVED".equals(o.getStatus()))
                .collect(Collectors.toList());
    }

    @PostMapping("/update-status")
    public ResponseEntity<?> updateStatus(@RequestBody Map<String, Object> payload) {
        Long orderId = Long.valueOf(payload.get("orderId").toString());
        String status = (String) payload.get("status");
        String chefId = (String) payload.get("chefId");
        Integer time = payload.get("time") != null ? Integer.valueOf(payload.get("time").toString()) : null;

        return orderRepository.findById(orderId).map(order -> {
            order.setStatus(status);
            if (chefId != null)
                order.setChefId(chefId);
            if (time != null)
                order.setEstimatedTimeMinutes(time);
            return ResponseEntity.ok(orderRepository.save(order));
        }).orElse(ResponseEntity.notFound().build());
    }
}
