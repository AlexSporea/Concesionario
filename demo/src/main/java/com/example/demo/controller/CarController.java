package com.example.demo.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Car;
import com.example.demo.service.CarPdfExporter;
import com.example.demo.service.CarService;
import com.example.demo.service.ValidationService;

@RestController
@RequestMapping
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/mostrarTodos")
    public List<Car> getCars() {
        return carService.getCars();
    }

    @PostMapping("/darAlta")
    public String addNewCar(@RequestBody Car car) {
        return carService.addNewCar(car);
    }

    @GetMapping("/mostrar/{marca}")
    public List<Car> getCarsByMarca(@PathVariable(name = "marca") String marca) {
        return carService.getCarsByMarca(marca);
    }

    @GetMapping(value = "/generarPdf/{marca}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generatePdf(@PathVariable(name = "marca") String marca,
            HttpServletResponse response) throws IOException {
                ValidationService.checkMarca(marca);
                
                HttpHeaders headers = new HttpHeaders();
                CarPdfExporter exporter = new CarPdfExporter(carService.getCarsByMarca(marca));
                ByteArrayInputStream bis = exporter.carsReport();

                headers.add(
                    "Content-Disposition",
                    ("attachment;filename=Fichero " + marca + ".pdf"));

                return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis));

    }

}
