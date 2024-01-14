package com.example.flightapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDTO {
    private String email;
    private String name;
    private String surname;
    private String password;
}
