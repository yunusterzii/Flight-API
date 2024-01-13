package com.example.flightapp.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "AIRPORT")
@Data
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "city")
    private String city;

    @OneToMany(mappedBy = "departureAirport", cascade = CascadeType.ALL)
    private Set<Flight> departuring_flights;

    @OneToMany(mappedBy = "returnAirport", cascade = CascadeType.ALL)
    private Set<Flight> returning_flights;
}
