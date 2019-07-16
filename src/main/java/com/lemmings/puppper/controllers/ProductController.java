package com.lemmings.puppper.controllers;



import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ProductController {

    @GetMapping("/invoice")
    public String getName(Model model) {
        model.addAttribute("sales", sale());
        model.addAttribute("invoice", new Invoice(3, new Date()));
        model.addAttribute("user", new User("Vasya", 150, true));
        model.addAttribute("saleOut", saleOut());
        return "invoice";
    }

    public SaleOut saleOut() {
        Product product = new Product("Фен", 300);
        return new SaleOut(product, product.getPrice() / 2);
    }

    public List<Sale> sale() {
        List<Sale> list = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            Product product = new Product("пылесос" + i, 100 + i);
            list.add(new Sale(product, 3));
        }
        return list;
    }

}

@Getter
class Product {
    private String name;
    private int price;

    Product(String name, int price) {
        this.name = name;
        this.price = price;
    }
}

@Getter
class Invoice {
    private int id;
    private Date date;

    Invoice(int id, Date date) {
        this.id = id;
        this.date = date;
    }
}

@Getter
class Sale {
    private Product product;
    private int count;
    private int cost;

    Sale(Product product, int count) {
        this.product = product;
        this.count = count;
        this.cost = product.getPrice() * count;
    }
}

@Getter
class SaleOut {
    private Product product;
    private int saleOutPrice;

    public SaleOut(Product product, int saleOutPrice) {
        this.product = product;
        this.saleOutPrice = saleOutPrice;
    }
}

@Getter
class User {
    private String name;
    private int bonus;
    private boolean isBonus;

    public User(String name, int bonus, boolean isBonus) {
        this.name = name;
        this.bonus = bonus;
        this.isBonus = isBonus;
    }

    public User(String name, boolean isBonus) {
        this.name = name;
        this.isBonus = isBonus;
    }

    public boolean isBonus() {
        return isBonus;
    }
}



