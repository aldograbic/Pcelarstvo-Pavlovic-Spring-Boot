package com.honeywebsite.project.controllers;

import java.util.List;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.honeywebsite.project.classes.product.Product;
import com.honeywebsite.project.classes.product.ProductRepository;
import com.honeywebsite.project.classes.review.Review;
import com.honeywebsite.project.classes.review.ReviewRepository;

@Controller
public class ProductPageController {

    private ProductRepository productRepository;
    private ReviewRepository reviewRepository;

    public ProductPageController(ProductRepository productRepository, ReviewRepository reviewRepository, JavaMailSender javaMailSender) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    @GetMapping("/proizvodi/{type}-{id}")
    public String showProductPage(@PathVariable("id") int productId, Model model) {

        Product product = productRepository.getProductById(productId);
        model.addAttribute("product", product);
        model.addAttribute("review", new Review());

        double averageReviewStar = reviewRepository.getAverageReviewById(productId);
        model.addAttribute("averageReviewStar", averageReviewStar);

        List<Review> limitedProductReviews = reviewRepository.getLimitedProductReviews(productId);
        model.addAttribute("limitedProductReviews", limitedProductReviews);

        List<Review> allProductReviews = reviewRepository.getAllProductReviews(productId);
        model.addAttribute("allProductReviews", allProductReviews);

        return "product-page";
    }

    @PostMapping("/proizvodi/{id}/recenzija")
    public String submitReview(@PathVariable("id") int productId,
                            @ModelAttribute Review review,
                            RedirectAttributes redirectAttributes) {

        Product product = productRepository.getProductById(productId);

        review.setProductId(productId);
        reviewRepository.saveProductReview(review);
        
        redirectAttributes.addFlashAttribute("successMessage", "Hvala Vam na recenziji proizvoda: " + product.getName() + "!");
        return "redirect:/proizvodi/" + product.getType().toLowerCase() + '-' + productId;
    }
}