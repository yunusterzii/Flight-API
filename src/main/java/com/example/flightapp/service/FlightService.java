package com.example.flightapp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.flightapp.dto.FlightCreateRequestDTO;
import com.example.flightapp.dto.FlightGetRequestDTO;
import com.example.flightapp.dto.FlightReturnDTO;
import com.example.flightapp.dto.FlightSearchRequestDTO;
import com.example.flightapp.entity.Airport;
import com.example.flightapp.entity.Flight;
import com.example.flightapp.repository.AirportRepository;
import com.example.flightapp.repository.FlightRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;

    public void createFlight(FlightCreateRequestDTO flight) {
        if (flight.getReturnDate() != null && (flight.getDepartureDate().isAfter(flight.getReturnDate())
                || flight.getDepartureDate().isEqual(flight.getReturnDate()))) {
            throw new IllegalStateException("Return time should be after the departure time!");
        }
        Flight createdFlight = new Flight();
        createdFlight.setDepartureAirport(flight.getDepartureAirport());
        createdFlight.setReturnAirport(flight.getReturnAirport());
        createdFlight.setDepartureDate(flight.getDepartureDate());
        createdFlight.setReturnDate(flight.getReturnDate());
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
        updateFlight.setDepartureDate(flight.getDepartureDate());
        updateFlight.setReturnDate(flight.getReturnDate());
        updateFlight.setPrice(flight.getPrice());
        flightRepository.save(updateFlight);
    }

    public void deleteFlightById(Long id) {
        Flight flight = flightRepository.findById(id).get();

        Airport departureAirport = airportRepository.findById(flight.getDepartureAirport().getId()).get();
        departureAirport.getDeparturing_flights().remove(flight);
        airportRepository.save(departureAirport);

        Airport returnAirport = airportRepository.findById(flight.getReturnAirport().getId()).get();
        returnAirport.getReturning_flights().remove(flight);
        airportRepository.save(returnAirport);

        flightRepository.deleteById(id);
    }

    public List<FlightGetRequestDTO> searchFlights(FlightSearchRequestDTO searchDetail) {
        Airport departureAirport = searchDetail.getDepartureAirport();
        Airport returnAirport = searchDetail.getReturnAirport();
        LocalDateTime departureDate = searchDetail.getDepartureDate();
        LocalDateTime returnDate = searchDetail.getReturnDate();

        List<FlightGetRequestDTO> result = new ArrayList<FlightGetRequestDTO>();

        List<Flight> flights = flightRepository.search(departureAirport.getId(), returnAirport.getId(),
                departureDate, returnDate);
        for (Flight flight : flights) {
            FlightGetRequestDTO dto = new FlightGetRequestDTO();
            dto.setDepartureAirport(flight.getDepartureAirport());
            dto.setReturnAirport(flight.getReturnAirport());
            dto.setDepartureDate(flight.getDepartureDate());
            dto.setPrice(flight.getPrice());
            if (returnDate != null && flight.getReturnDate() != null) {
                FlightReturnDTO dto2 = new FlightReturnDTO();
                dto2.setDepartureAirport(flight.getReturnAirport());
                dto2.setReturnAirport(flight.getDepartureAirport());
                dto2.setDepartureDate(flight.getReturnDate());
                dto.setReturnFlight(dto2);
                result.add(dto);
            } else if (returnDate == null && flight.getReturnDate() == null) {
                result.add(dto);
            }
        }
        return result;
    }
}
