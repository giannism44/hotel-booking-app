package gr.aueb.cf.hotelapp.dto;

import jakarta.validation.constraints.NotEmpty;

public record EmployeeUpdateDTO(
        Long id,

        @NotEmpty(message = "Το όνομα είναι υποχρεωτικό")
        String firstname,

        @NotEmpty(message = "Το επίθετο είναι υποχρεωτικό")
        String lastname
) {}
