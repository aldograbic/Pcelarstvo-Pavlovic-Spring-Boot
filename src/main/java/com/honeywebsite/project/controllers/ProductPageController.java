package com.honeywebsite.project.controllers;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.mail.SimpleMailMessage;
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
import com.honeywebsite.project.classes.purchase.Purchase;
import com.honeywebsite.project.classes.review.Review;
import com.honeywebsite.project.classes.review.ReviewRepository;

@Controller
public class ProductPageController {

    private ProductRepository productRepository;
    private ReviewRepository reviewRepository;
    private final JavaMailSender javaMailSender;
    private final ExecutorService executorService;

    public ProductPageController(ProductRepository productRepository, ReviewRepository reviewRepository, JavaMailSender javaMailSender) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.javaMailSender = javaMailSender;
        this.executorService = Executors.newFixedThreadPool(10);
    }

    @GetMapping("/proizvodi/{id}")
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
        return "redirect:/proizvodi/" + productId;
    }

    @PostMapping("/proizvodi/{id}/naruci")
    public String submitPurchase(@PathVariable("id") int productId,
                            @ModelAttribute Purchase purchase,
                            RedirectAttributes redirectAttributes) {

        Product product = productRepository.getProductById(productId);

        purchase.setProductId(productId);
        productRepository.savePurchaseDetails(purchase);

        executorService.execute(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("aldo.grabic99@gmail.com");
            message.setTo("aldo.grabic99@gmail.com");
            message.setSubject("Nova narudžba za proizvod: " + product.getName() + "!");
            message.setText("Detalji narudžbe:\n\n" +
                    "Proizvod: " + product.getName() + "\n" +
                    "Količina: " + purchase.getAmount() + "\n" +
                    "Ukupna cijena narudžbe: " + purchase.getTotalPrice() + "€" + "\n\n" +
                    "Kupac: " + purchase.getFirstName() + " " + purchase.getLastName() + "\n" +
                    "Adresa: " + purchase.getAddress() + ", " + purchase.getCity() + "\n" +
                    "Poruka: " + purchase.getMessage());

            javaMailSender.send(message);
        });
        
        redirectAttributes.addFlashAttribute("successMessage", "Hvala Vam na naručivanju proizvoda: " + product.getName() + "! Ubrzo ćemo vas kontaktirati.");
        return "redirect:/proizvodi/" + productId;
    }
}
