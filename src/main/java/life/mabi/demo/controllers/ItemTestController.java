package life.mabi.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ItemTestController {

    @GetMapping("/items")
    public String getItems(Model model) {
        List<Item> items = new ArrayList<>();
        // Add sample items
        items.add(new Item(1, "Item 1", "Description 1"));
        items.add(new Item(2, "Item 2", "Description 2"));
        items.add(new Item(3, "Item 3", "Description 3"));

        model.addAttribute("items", items);
        return "items"; // This corresponds to items.html
    }

    // Item class (you can also create a separate file for this)
    public static class Item {
        private int id;
        private String name;
        private String description;

        public Item(int id, String name, String description) {
            this.id = id;
            this.name = name;
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }
    }
}