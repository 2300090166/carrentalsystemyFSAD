package com.carlux.contactservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.carlux.contactservice.model.Contact;
import com.carlux.contactservice.repository.ContactRepository;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private JavaMailSender mailSender;

    public void saveContact(Contact contact) {
        contactRepository.save(contact);
        sendConfirmationEmail(contact.getEmail(), contact.getName());
    }

    private void sendConfirmationEmail(String to, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Thank You for Contacting Us");
        message.setText("Dear " + name + ",\n\nThank you for reaching out. We'll get back to you shortly.\n\nBest Regards,\nCarLux Team");
        mailSender.send(message);
    }
}