package com.example.flightapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.flightapp.dto.AirportCreateRequestDTO;
import com.example.flightapp.entity.Airport;
import com.example.flightapp.entity.Flight;
import com.example.flightapp.repository.AirportRepository;
import com.example.flightapp.repository.FlightRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AirportService {
    private final AirportRepository airportRepository;
    private final FlightRepository flightRepository;

    public Airport getAirportById(Long id) {
        Optional<Airport> airport = airportRepository.findById(id);
        if (airport.isEmpty()) {
            throw new IllegalArgumentException("Id is not found!");
        }
        return airport.get();
    }

    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }

    public void createAirport(AirportCreateRequestDTO airport) {
        Airport newAirport = new Airport();
        newAirport.setCity(airport.getCity());
        airportRepository.save(newAirport);
    }

    public void updateAirportById(Long id, AirportCreateRequestDTO airport) {
        Airport updateAirport = getAirportById(id);
        updateAirport.setCity(airport.getCity());
        airportRepository.save(updateAirport);
    }

    public void deleteAirportById(Long id) {
        for (Flight flight : flightRepository.findFlightsFromDeparturedAirprot(id)) {
            flightRepository.deleteById(flight.getId());
        }
        for (Flight flight : flightRepository.findFlightsFromReturningAirprot(id)) {
            flightRepository.deleteById(flight.getId());
        }
        airportRepository.deleteById(id);
    }
}
