package com.takeout.config;

import com.takeout.model.Employee;
import com.takeout.model.MenuItem;
import com.takeout.repository.EmployeeRepository;
import com.takeout.repository.MenuItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(MenuItemRepository menuRepo, EmployeeRepository empRepo) {
        return args -> {
            // Seed Menu
            // Always clear old menu to ensure updates apply (for dev/demo purpose)
            menuRepo.deleteAll();

            if (menuRepo.count() == 0) {
                List<MenuItem> items = new ArrayList<>();
                Random rand = new Random();

                // BREAKFAST (20)
                String[] breakfastItems = {
                        "Idli + Sambar", "Masala Dosa", "Poha", "Vegetable Omelette + Toast", "Oats Porridge",
                        "Upma", "Pancakes", "Aloo Paratha + Curd", "Smoothie Bowl", "Cornflakes + Milk",
                        "Bread Butter Jam", "Medu Vada", "Veg Sandwich", "Banana Peanut Butter Toast",
                        "Egg Bhurji + Roti",
                        "Cheese Omelette", "Fruit Salad", "Puri Bhaji", "Avocado Toast", "Curd Rice"
                };
                for (String name : breakfastItems) {
                    String img = "images/breakfast.jpg"; // Default
                    if (name.contains("Idli"))
                        img = "images/breakfast_idli.jpg";
                    else if (name.contains("Dosa"))
                        img = "images/breakfast_dosa.jpg";
                    else if (name.contains("Poha"))
                        img = "images/breakfast_poha.jpg";
                    else if (name.contains("Omelette"))
                        img = "images/breakfast_omelette.jpg";
                    else if (name.contains("Pancakes"))
                        img = "images/breakfast_pancakes.jpg";
                    else if (name.contains("Paratha"))
                        img = "images/breakfast_paratha.jpg";
                    else if (name.contains("Smoothie"))
                        img = "images/breakfast_smoothie.jpg";
                    else if (name.contains("Sandwich"))
                        img = "images/breakfast_sandwich.jpg";
                    else if (name.contains("Fruit"))
                        img = "images/breakfast_fruit.jpg";
                    else if (name.contains("Toast"))
                        img = "images/breakfast_toast.jpg";

                    items.add(createItem(name, "Delicious breakfast item", rand.nextInt(100) + 1,
                            MenuItem.Category.BREAKFAST, img));
                }

                // LUNCH (20) - Note: Curd Rice appeared in Breakfast too, keeping it based on
                // user list
                String[] lunchItems = {
                        "Veg Thali", "Chicken Biryani", "Rajma Chawal", "Veg Pulao", "Fish Curry + Rice",
                        "Paneer Butter Masala + Naan", "Dal Tadka + Rice", "Lemon Rice", "Chicken Curry + Rice",
                        "Veg Fried Rice",
                        "Sambar Rice", "Curd Rice", "Chole Bhature", "Khichdi + Ghee", "Mutton Curry + Rice",
                        "Veg Biryani", "Palak Paneer + Roti", "Egg Curry + Rice", "Tandoori Chicken + Naan",
                        "Tomato Rice"
                };
                for (String name : lunchItems) {
                    String img = "images/lunch.jpg"; // Default
                    if (name.contains("Biryani") || name.contains("Pulao"))
                        img = "images/lunch_biryani.jpg";
                    else if (name.contains("Rice"))
                        img = "images/lunch_rice.jpg";
                    else if (name.contains("Paneer"))
                        img = "images/lunch_paneer.jpg";
                    else if (name.contains("Curry") || name.contains("Dal") || name.contains("Rajma")
                            || name.contains("Chole"))
                        img = "images/lunch_curry.jpg";

                    items.add(createItem(name, "Hearty lunch meal", rand.nextInt(100) + 1, MenuItem.Category.LUNCH,
                            img));
                }

                // SNACKS (20)
                String[] snackItems = {
                        "Samosa", "Veg Cutlet", "Pani Puri", "Bhel Puri", "Pakora",
                        "Vada Pav", "Grilled Sandwich", "Popcorn", "French Fries", "Chana Chaat",
                        "Peanut Chaat", "Biscuits + Tea", "Spring Rolls", "Veg Puff", "Banana Chips",
                        "Roasted Makhana", "Paneer Tikka", "Corn Chaat", "Milkshake", "Brownies"
                };
                for (String name : snackItems) {
                    String img = "images/snacks.jpg"; // Default

                    if (name.contains("Samosa") || name.contains("Puff"))
                        img = "images/snacks_samosa.jpg";
                    else if (name.contains("Cutlet") || name.contains("Pakora") || name.contains("Vada"))
                        img = "images/snacks_fried.jpg";
                    else if (name.contains("Puri") || name.contains("Chaat"))
                        img = "images/snacks_chaat.jpg";
                    else if (name.contains("Sandwich") || name.contains("Burger"))
                        img = "images/snacks_burger.jpg";
                    else if (name.contains("Fries") || name.contains("Chips") || name.contains("Popcorn")
                            || name.contains("Makhana"))
                        img = "images/snacks_light.jpg";
                    else if (name.contains("Spring Rolls"))
                        img = "images/snacks_rolls.jpg";
                    else if (name.contains("Tikka"))
                        img = "images/snacks_tikka.jpg";
                    else if (name.contains("Milkshake") || name.contains("Brownies") || name.contains("Biscuits"))
                        img = "images/snacks_dessert.jpg";

                    items.add(createItem(name, "Tasty snack", rand.nextInt(100) + 1, MenuItem.Category.SNACK,
                            img));
                }

                // DINNER (20)
                String[] dinnerItems = {
                        "Roti + Veg Curry", "Chicken Curry + Roti", "Veg Noodles", "Paneer Tikka Masala + Naan",
                        "Dal + Roti",
                        "Chicken Fried Rice", "Soup + Garlic Bread", "Fish Fry + Rice", "Stuffed Paratha",
                        "Idli / Dosa",
                        "Pasta", "Veg Burger + Fries", "Shawarma", "Kadhi Chawal", "Appam + Veg Stew",
                        "Egg Fried Rice", "Mushroom Curry + Roti", "Chicken Wrap", "Veg Khichdi", "Tomato Soup + Toast"
                };
                for (String name : dinnerItems) {
                    String img = "images/dinner.jpg"; // Default
                    if (name.contains("Pizza"))
                        img = "images/dinner_pizza.jpg";
                    else if (name.contains("Pasta") || name.contains("Noodles"))
                        img = "images/dinner_pasta.jpg";
                    else if (name.contains("Burger") || name.contains("Wrap") || name.contains("Shawarma"))
                        img = "images/dinner_burger.jpg";
                    else if (name.contains("Curry") || name.contains("Masala") || name.contains("Kadhi")
                            || name.contains("Stew"))
                        img = "images/dinner_curry.jpg";
                    else if (name.contains("Rice") || name.contains("Biryani"))
                        img = "images/lunch_rice.jpg";

                    items.add(createItem(name, "Exquisite dinner", rand.nextInt(100) + 1, MenuItem.Category.DINNER,
                            img));
                }

                menuRepo.saveAll(items);
            }

            // Seed Employees
            if (empRepo.count() == 0) {
                Employee e1 = new Employee();
                e1.setId("12345678");
                e1.setName("John Doe");
                e1.setRole("Server");
                e1.setDepartment("Service");
                e1.setSalary(15000.0);
                e1.setPerformanceRating(5.0);
                e1.setGoodStandingCount(0);
                e1.setDaysPresent(0);
                e1.setPassword("password");

                Employee head = new Employee();
                head.setId("87654321");
                head.setName("Jane Manager");
                head.setRole("Head");
                head.setDepartment("Management");
                head.setSalary(45000.0);
                head.setPerformanceRating(9.0);
                head.setPassword("SECRET"); // In real app, hash this

                empRepo.saveAll(List.of(e1, head));
            }
        };
    }

    private MenuItem createItem(String name, String desc, double price, MenuItem.Category category, String imageUrl) {
        MenuItem m = new MenuItem();
        m.setName(name);
        m.setDescription(desc);
        m.setPrice(price);
        m.setCategory(category);
        m.setImageUrl(imageUrl);

        // Auto-generate ingredients based on name
        String ing = "Secret Chef's Blend";
        if (name.contains("Idli"))
            ing = "Rice, Urad Dal, Fenugreek, Salt";
        else if (name.contains("Dosa"))
            ing = "Rice Batter, Potato Masala, Onions, Curry Leaves";
        else if (name.contains("Biryani"))
            ing = "Basmati Rice, Saffron, Spices, Fried Onions, Ghee";
        else if (name.contains("Paneer"))
            ing = "Fresh Paneer, Tomato Gravy, Cashews, Cream";
        else if (name.contains("Pizza"))
            ing = "Flour, Mozzarella, Tomato Sauce, Basil, Oregano";
        else if (name.contains("Burger"))
            ing = "Bun, Patty, Lettuce, Tomato, Cheese, Secret Sauce";
        else if (name.contains("Pasta"))
            ing = "Pasta, Olive Oil, Garlic, Parmesan, Herbs";

        m.setIngredients(ing);
        return m;
    }
}
