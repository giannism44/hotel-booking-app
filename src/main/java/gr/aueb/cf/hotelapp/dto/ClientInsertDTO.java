package gr.aueb.cf.hotelapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

/**
 * DTO για την εισαγωγή νέου πελάτη μέσω φορμών.
 * Περιλαμβάνει validation για email, κωδικό, τηλέφωνο και ΑΦΜ.
 */
public record ClientInsertDTO(
        @Email(message = "Το username πρέπει να είναι έγκυρο email")
        String username,

        @Pattern(regexp = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\d)(?=.*?[@#$!%&*]).{8,}$",
                message = "Εισάγεται έγκυρο κωδικό")
        String password,

        @NotEmpty(message = "Το όνομα είναι υποχρεωτικό")
        String firstname,

        @NotEmpty(message = "Το επίθετο είναι υποχρεωτικό")
        String lastname,

        @Pattern(regexp = "^\\d{10}$", message = "Το τηλέφωνο πρέπει να έχει 10 ψηφία")
        String phone,

        @Pattern(regexp = "^\\d{9}$", message = "Το ΑΦΜ πρέπει να έχει 9 ψηφία")
        String vat
) {}
