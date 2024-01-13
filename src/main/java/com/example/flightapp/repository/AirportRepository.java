package com.example.flightapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.flightapp.entity.Airport;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {

}
