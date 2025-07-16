package com.carlux.carservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.carlux.carservice.model.Car;

public interface CarRepository extends JpaRepository<Car, Long> {
}