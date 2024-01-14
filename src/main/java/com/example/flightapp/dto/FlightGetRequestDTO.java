package com.example.flightapp.dto;

import java.time.LocalDateTime;

import com.example.flightapp.entity.Airport;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightGetRequestDTO {
    private Airport departureAirport;
    private Airport returnAirport;
    private LocalDateTime departureDate;
    private Long price;
    private FlightReturnDTO returnFlight;
}
