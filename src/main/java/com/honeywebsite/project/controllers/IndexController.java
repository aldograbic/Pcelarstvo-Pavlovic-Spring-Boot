package com.honeywebsite.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.honeywebsite.project.classes.product.Product;
import com.honeywebsite.project.classes.product.ProductRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public String showIndex(@RequestParam(name = "uspjesno", required = false) Boolean uspjesno,
                            HttpSession session, Model model) {

        if (Boolean.TRUE.equals(uspjesno)) {
            session.removeAttribute("cart");
        }

        List<Product> featuredProducts = productRepository.getFeaturedProducts();
        model.addAttribute("featuredProducts", featuredProducts);

        return "index";
    }
}