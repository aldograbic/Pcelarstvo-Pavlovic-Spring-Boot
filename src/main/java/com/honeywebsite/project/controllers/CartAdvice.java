package com.honeywebsite.project.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.honeywebsite.project.classes.product.CartItem;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class CartAdvice {

    @ModelAttribute("cart")
    public List<CartItem> getCart(HttpSession session) {
        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        return cart != null ? cart : new ArrayList<>();
    }

    @ModelAttribute("totalCartPrice")
    public BigDecimal totalCartPrice(HttpSession session) {
        List<CartItem> cart = getCart(session);
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cart) {
            BigDecimal itemTotal = item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(itemTotal);
        }
        return total;
    }
}