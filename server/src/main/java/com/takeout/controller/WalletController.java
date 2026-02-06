package com.takeout.controller;

import com.takeout.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/api/wallet")
@CrossOrigin(origins = "*")
public class WalletController {

    @Autowired
    private UserRepository userRepository;

    private final Random random = new Random();

    @PostMapping("/generate-reward")
    public ResponseEntity<?> generateReward(@RequestBody Map<String, Long> payload) {
        Long userId = payload.get("userId");

        return userRepository.findById(userId).map(user -> {
            // Random coins 100 - 499
            int coins = random.nextInt(400) + 100;
            user.setWalletBalance(user.getWalletBalance() + coins);

            // Assign QR string if not present
            if (user.getQrCodeString() == null) {
                user.setQrCodeString(UUID.randomUUID().toString());
            }

            userRepository.save(user);
            return ResponseEntity.ok(Map.of("coins", coins, "total", user.getWalletBalance()));
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getWallet(@PathVariable Long userId) {
        return userRepository.findById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
