package com.takeout.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class FoodOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; // Link to User

    @Column(length = 2000)
    private String itemsSummary; // Storing as simple text/JSON for now e.g. "2x Burger, 1x Pasta"

    private Double totalPrice;
    private String status; // NEW, COOKING, READY, SERVED

    // Phase 2: Chef & Timing
    private String chefId; // Employee ID of chef
    private Integer estimatedTimeMinutes; // e.g. 15 mins

    private LocalDateTime orderTime;

    @PrePersist
    protected void onCreate() {
        orderTime = LocalDateTime.now();
    }

    public FoodOrder() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getItemsSummary() {
        return itemsSummary;
    }

    public void setItemsSummary(String itemsSummary) {
        this.itemsSummary = itemsSummary;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChefId() {
        return chefId;
    }

    public void setChefId(String chefId) {
        this.chefId = chefId;
    }

    public Integer getEstimatedTimeMinutes() {
        return estimatedTimeMinutes;
    }

    public void setEstimatedTimeMinutes(Integer estimatedTimeMinutes) {
        this.estimatedTimeMinutes = estimatedTimeMinutes;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    @Override
    public String toString() {
        return "FoodOrder{" +
                "id=" + id +
                ", userId=" + userId +
                ", itemsSummary='" + itemsSummary + '\'' +
                ", totalPrice=" + totalPrice +
                ", status='" + status + '\'' +
                ", chefId='" + chefId + '\'' +
                ", estimatedTimeMinutes=" + estimatedTimeMinutes +
                ", orderTime=" + orderTime +
                '}';
    }
}
