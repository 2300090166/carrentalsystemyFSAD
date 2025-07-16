package com.carlux.BookingService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carlux.BookingService.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
}