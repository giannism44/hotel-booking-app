package gr.aueb.cf.hotelapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record EmployeeInsertDTO(
        @Email(message = "Το username πρέπει να είναι έγκυρο email")
        String username,

        @Pattern(regexp = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\d)(?=.*?[@#$!%&*]).{8,}$",
                message = "Εισάγεται έγκυρο κωδικό")
        String password,

        @NotEmpty(message = "Το όνομα είναι υποχρεωτικό")
        String firstname,

        @NotEmpty(message = "Το επίθετο είναι υποχρεωτικό")
        String lastname
) {}

