package com.takeout.config;

import com.takeout.model.Employee;
import com.takeout.model.MenuItem;
import com.takeout.repository.EmployeeRepository;
import com.takeout.repository.FeedbackRepository;
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
    CommandLineRunner initData(MenuItemRepository menuRepo, EmployeeRepository empRepo,
            FeedbackRepository feedbackRepo) {
        return args -> {
            System.out.println("🚀 [DataInitializer] Starting Data Seeding...");

            // Seed Menu
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

                    // Generate round price between 50 and 300 (multiples of 10)
                    // (rand.nextInt(26) * 10) gives 0..250. + 50 gives 50..300.
                    double price = (rand.nextInt(26) * 10) + 50;

                    items.add(createItem(name, "Delicious breakfast item", price,
                            MenuItem.Category.BREAKFAST, img));
                }

                // LUNCH (20)
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

                    double price = (rand.nextInt(26) * 10) + 50;
                    items.add(createItem(name, "Hearty lunch meal", price, MenuItem.Category.LUNCH,
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
                    else if (name.contains("Cutlet"))
                        img = "images/snacks_cutlet.jpg";
                    else if (name.contains("Pakora"))
                        img = "images/snacks_pakora.jpg";
                    else if (name.contains("Vada"))
                        img = "images/snacks_vada_pav.jpg";
                    else if (name.contains("Pani Puri"))
                        img = "images/snacks_pani_puri.jpg";
                    else if (name.contains("Bhel Puri") || name.contains("Chaat"))
                        img = "images/snacks_bhel_puri.jpg";
                    else if (name.contains("Sandwich"))
                        img = "images/snacks_sandwich.jpg";
                    else if (name.contains("Burger"))
                        img = "images/snacks_burger.jpg";
                    else if (name.contains("Fries"))
                        img = "images/snacks_chips.jpg";
                    else if (name.contains("Popcorn"))
                        img = "images/snacks_popcorn.jpg";
                    else if (name.contains("Chips"))
                        img = "images/snacks_chips.jpg";
                    else if (name.contains("Makhana"))
                        img = "images/snacks_makhana.jpg";
                    else if (name.contains("Spring Rolls"))
                        img = "images/snacks_rolls.jpg";
                    else if (name.contains("Tikka"))
                        img = "images/snacks_tikka.jpg";
                    else if (name.contains("Milkshake"))
                        img = "images/snacks_milkshake.jpg";
                    else if (name.contains("Brownies"))
                        img = "images/snacks_brownie.jpg";
                    else if (name.contains("Biscuits"))
                        img = "images/snacks_dessert.jpg";

                    double price = (rand.nextInt(16) * 10) + 20; // Snacks cheaper: 20-170
                    items.add(createItem(name, "Tasty snack", price, MenuItem.Category.SNACK,
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

                    double price = (rand.nextInt(26) * 10) + 50;
                    items.add(createItem(name, "Exquisite dinner", price, MenuItem.Category.DINNER,
                            img));
                }

                menuRepo.saveAll(items);
            }

            // Seed Employees - ALWAYS WIPE AND RE-SEED
            feedbackRepo.deleteAll(); // Fix FK constraint
            empRepo.deleteAll();
            List<Employee> employees = new ArrayList<>();

            // Option 1: Numeric (Original)
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
            employees.add(e1);

            Employee head1 = new Employee();
            head1.setId("87654321");
            head1.setName("Jane Manager");
            head1.setRole("Head"); // Admin privilege
            head1.setDepartment("Management");
            head1.setSalary(45000.0);
            head1.setPerformanceRating(9.0);
            head1.setPassword("SECRET");
            employees.add(head1);

            // Option 2: Alphanumeric (Easier to remember)
            Employee e2 = new Employee();
            e2.setId("staff01");
            e2.setName("Demo Staff");
            e2.setRole("Server");
            e2.setDepartment("Service");
            e2.setSalary(15000.0);
            e2.setPassword("staff123");
            employees.add(e2);

            Employee head2 = new Employee();
            head2.setId("admin01");
            head2.setName("Demo Admin");
            head2.setRole("Head"); // Ensure role matches Admin logic
            head2.setDepartment("Management");
            head2.setSalary(45000.0);
            head2.setPassword("admin123");
            employees.add(head2);

            empRepo.saveAll(employees);
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
