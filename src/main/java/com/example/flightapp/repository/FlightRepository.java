package com.example.flightapp.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.flightapp.entity.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query(value = "SELECT * FROM FLIGHT f WHERE f.DEPARTURE_AIRPORT_ID=?1 and f.RETURN_AIRPORT_ID=?2 and f.DEPARTURE_DATE=?3 and (f.RETURN_DATE is null or f.RETURN_DATE=?4)", nativeQuery = true)
    List<Flight> search(Long departureAirportId, Long returnAirportId, LocalDateTime departureDate,
            LocalDateTime returnDate);

    @Query(value = "SELECT * FROM FLIGHT f WHERE f.DEPARTURE_AIRPORT_ID=?1", nativeQuery = true)
    List<Flight> findFlightsFromDeparturedAirprot(Long departureAirportId);

    @Query(value = "SELECT * FROM FLIGHT f WHERE f.RETURN_AIRPORT_ID=?1", nativeQuery = true)
    List<Flight> findFlightsFromReturningAirprot(Long returnAirportId);

}
