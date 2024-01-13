package com.example.flightapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.flightapp.dto.FlightCreateRequestDTO;
import com.example.flightapp.dto.FlightSearchRequestDTO;
import com.example.flightapp.entity.Airport;
import com.example.flightapp.entity.Flight;
import com.example.flightapp.repository.FlightRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;

    public void createFlight(FlightCreateRequestDTO flight) {
        if (flight.getDepartureTime().isAfter(flight.getReturnTime())
                || flight.getDepartureTime().isEqual(flight.getReturnTime())) {
            throw new IllegalStateException("Return time should be after the departure time!");
        }
        Flight createdFlight = new Flight();
        createdFlight.setDepartureAirport(flight.getDepartureAirport());
        createdFlight.setReturnAirport(flight.getReturnAirport());
        createdFlight.setDepartureTime(flight.getDepartureTime());
        createdFlight.setReturnTime(flight.getReturnTime());
        createdFlight.setPrice(flight.getPrice());

        flightRepository.save(createdFlight);
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Flight getFlightById(Long id) {
        Optional<Flight> flight = flightRepository.findById(id);
        if (flight.isEmpty()) {
            throw new IllegalArgumentException("Id is not found!");
        }
        return flight.get();
    }

    public void updateFlightById(Long id, FlightCreateRequestDTO flight) {
        Flight updateFlight = getFlightById(id);
        updateFlight.setDepartureAirport(flight.getDepartureAirport());
        updateFlight.setReturnAirport(flight.getReturnAirport());
        updateFlight.setDepartureTime(flight.getDepartureTime());
        updateFlight.setReturnTime(flight.getReturnTime());
        updateFlight.setPrice(flight.getPrice());
        flightRepository.save(updateFlight);
    }

    public void deleteFlightById(Long id) {
        flightRepository.deleteById(id);
    }

    public List<Flight> searchFlights(FlightSearchRequestDTO searchDetail) {
        Airport departureAirport = searchDetail.getDepartureAirport();
        Airport returnAirport = searchDetail.getReturnAirport();
        LocalDateTime departureDate = searchDetail.getDepartureDate();
        LocalDateTime returnDate = searchDetail.getReturnDate();

        return flightRepository.search(departureAirport, returnAirport, departureDate, returnDate);
    }
}
