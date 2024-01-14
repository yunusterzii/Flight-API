package com.example.flightapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.flightapp.dto.AirportCreateRequestDTO;
import com.example.flightapp.entity.Airport;
import com.example.flightapp.service.AirportService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/airport")
@AllArgsConstructor
public class AirportController {
    private final AirportService airportService;

    @GetMapping("/get/{id}")
    public Airport getAirportById(@PathVariable Long id) {
        return airportService.getAirportById(id);
    }

    @GetMapping("/getAll")
    public List<Airport> getAllAirports() {
        return airportService.getAllAirports();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAirport(@RequestBody AirportCreateRequestDTO airport) {
        airportService.createAirport(airport);
        return new ResponseEntity<>("Airport created successfully", HttpStatus.OK);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<String> updateAirportById(@PathVariable Long id,
            @RequestBody AirportCreateRequestDTO airport) {
        if (getAirportById(id) == null) {
            return new ResponseEntity<>("Airport not found!", HttpStatus.BAD_REQUEST);
        }
        airportService.updateAirportById(id, airport);
        return new ResponseEntity<>("Airport updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteAirportById(@PathVariable Long id) {
        if (getAirportById(id) == null) {
            return new ResponseEntity<>("Airport not found!", HttpStatus.BAD_REQUEST);
        }
        airportService.deleteAirportById(id);
        return new ResponseEntity<>("Airport deleted successfully", HttpStatus.OK);
    }

}
