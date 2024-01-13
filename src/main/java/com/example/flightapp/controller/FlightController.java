package com.example.flightapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.flightapp.dto.FlightCreateRequestDTO;
import com.example.flightapp.dto.FlightSearchRequestDTO;
import com.example.flightapp.entity.Flight;
import com.example.flightapp.service.FlightService;

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
@RequestMapping("/flight")
@AllArgsConstructor
public class FlightController {
    private final FlightService flightService;

    @GetMapping("/get/{id}")
    public Flight getFlightById(@PathVariable Long id) {
        return flightService.getFlightById(id);
    }

    @GetMapping("/getAll")
    public List<Flight> getAllFlights(@PathVariable Long id) {
        return flightService.getAllFlights();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createFlight(@RequestBody FlightCreateRequestDTO flight) {
        flightService.createFlight(flight);
        return new ResponseEntity<>("Flight created successfully", HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateFlightById(@PathVariable Long id, @RequestBody FlightCreateRequestDTO flight) {
        if (flightService.getFlightById(id) == null) {
            return new ResponseEntity<>("Flight is not found!", HttpStatus.BAD_REQUEST);
        }
        flightService.updateFlightById(id, flight);
        return new ResponseEntity<>("Flight updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFlightById(@PathVariable Long id) {
        if (flightService.getFlightById(id) == null) {
            return new ResponseEntity<>("Flight is not found!!", HttpStatus.BAD_REQUEST);
        }
        flightService.deleteFlightById(id);
        return new ResponseEntity<>("Flight deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/search")
    public List<Flight> searchFligths(@RequestBody FlightSearchRequestDTO searchDetail) {
        return flightService.searchFlights(searchDetail);
    }

}
