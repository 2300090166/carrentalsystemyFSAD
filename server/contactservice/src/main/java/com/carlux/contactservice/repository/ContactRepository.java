package com.carlux.contactservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.carlux.contactservice.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}