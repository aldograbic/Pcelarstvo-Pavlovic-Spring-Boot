package com.honeywebsite.project.controllers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.honeywebsite.project.classes.contact.Contact;
import com.honeywebsite.project.classes.contact.ContactRepository;

@Controller
public class ContactController {

private final ContactRepository contactRepository;
    private final JavaMailSender javaMailSender;
    private final ExecutorService executorService;

    @Value("${spring.mail.username}")
    private String emailVlasnika;

    public ContactController(ContactRepository contactRepository, JavaMailSender javaMailSender) {
        this.contactRepository = contactRepository;
        this.javaMailSender = javaMailSender;
        this.executorService = Executors.newFixedThreadPool(10);
    }

    @GetMapping("/kontakt")
    public String showContact() {
        return "contact";
    }

    @PostMapping("/kontakt")
    public String contact(@ModelAttribute Contact contact, RedirectAttributes redirectAttributes) {
        contactRepository.saveMessage(contact);

        executorService.execute(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailVlasnika);
            message.setTo(emailVlasnika);
            message.setSubject("Nova kontakt poruka!");
            message.setText("Detalji poruke:\n\n" +
                    "Ime: " + contact.getName() + "\n" +
                    "Email: " + contact.getEmail() + "\n" +
                    "Poruka: " + contact.getMessage());

            javaMailSender.send(message);
        });

        redirectAttributes.addFlashAttribute("successMessage", "Hvala vam što ste nas kontaktirali! Vaša poruka je uspješno poslana.");

        return "redirect:/";
    }
}
