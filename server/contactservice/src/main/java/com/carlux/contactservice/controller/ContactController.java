package com.carlux.contactservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.carlux.contactservice.model.Contact;
import com.carlux.contactservice.service.ContactService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping
    public String submitContactForm(@Valid @RequestBody Contact contact) {
        contactService.saveContact(contact);
        return "Contact form submitted successfully!";
    }
}