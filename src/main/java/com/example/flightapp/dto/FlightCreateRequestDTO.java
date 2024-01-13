package com.example.flightapp.dto;

import java.time.LocalDateTime;

import com.example.flightapp.entity.Airport;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FlightCreateRequestDTO {
    private Airport departureAirport;
    private Airport returnAirport;
    private LocalDateTime departureTime;
    private LocalDateTime returnTime;
    private Long price;
}
