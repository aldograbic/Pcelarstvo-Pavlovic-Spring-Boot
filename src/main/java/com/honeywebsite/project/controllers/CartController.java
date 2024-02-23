package com.honeywebsite.project.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.honeywebsite.project.classes.product.CartItem;
import com.honeywebsite.project.classes.product.Product;
import com.honeywebsite.project.classes.product.ProductRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/kosarica")
public class CartController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private HttpSession session;

    @PostMapping("/dodaj")
    public String addToCart(@RequestParam int productId, @RequestParam int amount, HttpSession session) {
        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        Product product = productRepository.getProductById(productId);
        CartItem existingItem = null;
        for (CartItem item : cart) {
            if (item.getProduct().getId() == productId) {
                existingItem = item;
                break;
            }
        }

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + amount);
        } else {
            CartItem newItem = new CartItem(product, amount);
            cart.add(newItem);
        }

        session.setAttribute("cart", cart);
        if (product != null) {
            return "redirect:/proizvodi/" + product.getType().toLowerCase() + '-' + productId;
        } else {
            return "redirect:/errorPage";
        }
    }

    @PostMapping("/izbrisi")
    public String removeFromCart(@RequestParam int productId, HttpSession session, HttpServletRequest request) {
        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            cart.removeIf(item -> item.getProduct().getId() == productId);
            session.setAttribute("cart", cart);
        }

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/");
    }

    @GetMapping("/kupnja")
    public String createCheckoutSession() throws StripeException {

        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

        for (CartItem item : cart) {
            lineItems.add(SessionCreateParams.LineItem.builder()
                .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                    .setCurrency("eur")
                    .setUnitAmount((long) (item.getProduct().getPrice().doubleValue() * 100))
                    .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName(item.getProduct().getName())
                        .build())
                    .build())
                .setQuantity((long) item.getQuantity())
                .build());
        }

        SessionCreateParams params = SessionCreateParams.builder()
            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl("http://localhost:8080?uspjesno=true")
            .setCancelUrl("http://localhost:8080/")
            .addAllLineItem(lineItems)
            .setShippingAddressCollection(
            SessionCreateParams.ShippingAddressCollection.builder()
            .addAllowedCountry(SessionCreateParams.ShippingAddressCollection.AllowedCountry.HR)
                
            .build())

        .build();

        Session session = Session.create(params);
        return "redirect:" + session.getUrl();
    }
}