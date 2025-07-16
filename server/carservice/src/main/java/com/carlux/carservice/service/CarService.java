package com.carlux.carservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.carlux.carservice.model.Car;
import com.carlux.carservice.repository.CarRepository;

import java.util.List;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCarById(Long id) {
        return carRepository.findById(id).orElseThrow(() -> new RuntimeException("Car not found"));
    }

    public Car createCar(Car car) {
        return carRepository.save(car);
    }

    public Car updateCar(Long id, Car updatedCar) {
        Car existingCar = getCarById(id);
        existingCar.setName(updatedCar.getName());
        existingCar.setType(updatedCar.getType());
        existingCar.setImageUrl(updatedCar.getImageUrl());
        existingCar.setShortDescription(updatedCar.getShortDescription());
        existingCar.setPricePerDay(updatedCar.getPricePerDay());
        return carRepository.save(existingCar);
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }
}