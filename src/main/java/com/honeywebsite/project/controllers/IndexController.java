package com.honeywebsite.project.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.honeywebsite.project.classes.product.Product;
import com.honeywebsite.project.classes.product.ProductRepository;

@Controller
public class IndexController {

    private ProductRepository productRepository;

    public IndexController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/")
    public String showIndex(Model model) {

        List<Product> featuredProducts = productRepository.getFeaturedProducts();
        model.addAttribute("featuredProducts", featuredProducts);

        return "index";
    }
}