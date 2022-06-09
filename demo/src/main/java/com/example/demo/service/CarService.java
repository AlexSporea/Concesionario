package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Car;
import com.example.demo.repository.CarRepository;

@Service
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getCars() {
        return carRepository.findAll();
    }

    public String addNewCar(Car car) {
       
        ValidationService.checkMarca(car.getMarca());
        ValidationService.checkRegex(
            "(\\w{4}-\\d{4})",
            car.getModelo(),
            "El modelo debe seguir la estructura AAAA-1111"
        );
        ValidationService.checkRegex(
            "(19|20[\\d]{2}\\s\\w{3})",
            car.getMatricula(),
            "La matrícula debe seguir la estructura 2020 ZAZ"
        );
        
        carRepository.save(car);
        return "¡Coche añadido con éxito!";
    }

    public List<Car> getCarsByMarca(String marca) {
        ValidationService.checkMarca(marca);
        return carRepository.getCarsByMarca(marca);
    }

}
