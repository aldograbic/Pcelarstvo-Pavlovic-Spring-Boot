package com.honeywebsite.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.honeywebsite.project.classes.product.Product;
import com.honeywebsite.project.classes.product.ProductRepository;

import java.util.Comparator;
import java.util.List;

@Controller
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/proizvodi")
    public String showProducts(@RequestParam(required = false) String type,
                            @RequestParam(required = false) Double minPrice,
                            @RequestParam(required = false) Double maxPrice,
                            @RequestParam(required = false) String sort,
                            Model model) {

        List<Product> products = productRepository.getProducts();
        model.addAttribute("products", products);

        int productCount = products.size();
        model.addAttribute("productCount", productCount);

        if ("min".equals(sort)) {
            products.sort(Comparator.comparing(Product::getPrice));
        } else if ("max".equals(sort)) {
            products.sort((l1, l2) -> l2.getPrice().compareTo(l1.getPrice()));
        }

        List<Product> filteredProducts;

        if ("Sve".equals(type)) {
            type = null;
        }

        if (type != null || minPrice != null || maxPrice != null) {
            filteredProducts = productRepository.filterByTypeAndPrice(type, minPrice, maxPrice);
            productCount = filteredProducts.size();
            model.addAttribute("products", filteredProducts);
            model.addAttribute("productCount", productCount);

            if ("min".equals(sort)) {
            filteredProducts.sort(Comparator.comparing(Product::getPrice));
            } else if ("max".equals(sort)) {
                filteredProducts.sort((l1, l2) -> l2.getPrice().compareTo(l1.getPrice()));
            }

        } else {
            filteredProducts = products;
        }

        model.addAttribute("selectedType", type);
        model.addAttribute("selectedMinPrice", minPrice);
        model.addAttribute("selectedMaxPrice", maxPrice);

        return "products";
    }
}