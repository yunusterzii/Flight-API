package com.example.flightapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "FLIGHT")
@Data

public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @ManyToOne(targetEntity = Airport.class)
    @JoinColumn(name = "departure_airport_id", referencedColumnName = "id", nullable = false)
    private Airport departureAirport;

    @ManyToOne(targetEntity = Airport.class)
    @JoinColumn(name = "return_airport_id", referencedColumnName = "id", nullable = false)
    private Airport returnAirport;

    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    @Column(name = "return_time")
    private LocalDateTime returnTime;

    @Column(name = "price", nullable = false)
    private Long price;

}