package com.takeout.controller;

import com.takeout.model.MenuItem;
import com.takeout.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
@CrossOrigin(origins = "*") // Allow consumption from static files easily
public class MenuItemController {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @GetMapping
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    @GetMapping("/category/{category}")
    public List<MenuItem> getByCategory(@PathVariable MenuItem.Category category) {
        return menuItemRepository.findByCategory(category);
    }

    // For initial data seeding (Admin usage really, but open for demo simplicity)
    @PostMapping
    public MenuItem createMenuItem(@RequestBody MenuItem item) {
        return menuItemRepository.save(item);
    }
}
