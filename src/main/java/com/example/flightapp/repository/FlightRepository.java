package com.example.flightapp.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.flightapp.entity.Airport;
import com.example.flightapp.entity.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query(value = "SELECT * FROM FLIGHT", nativeQuery = true)
    List<Flight> search(Airport departureAirport, Airport returnAirport, LocalDateTime departureDate,
            LocalDateTime returnDate);

}
