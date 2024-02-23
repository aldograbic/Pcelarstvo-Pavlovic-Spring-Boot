package com.honeywebsite.project.config;

import com.stripe.Stripe;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StripeConfiguration {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @PostConstruct
    public void setup() {
        Stripe.apiKey = stripeApiKey;
    }
}