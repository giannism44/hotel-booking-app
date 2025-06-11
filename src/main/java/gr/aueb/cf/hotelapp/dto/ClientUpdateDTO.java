package gr.aueb.cf.hotelapp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record ClientUpdateDTO(
        Long id,

        @NotEmpty(message = "Το όνομα είναι υποχρεωτικό")
        String firstname,

        @NotEmpty(message = "Το επίθετο είναι υποχρεωτικό")
        String lastname,

        @Pattern(regexp = "^\\d{10}$", message = "Το τηλέφωνο πρέπει να έχει 10 ψηφία")
        String phone,

        @Pattern(regexp = "^\\d{9}$", message = "Το ΑΦΜ πρέπει να έχει 9 ψηφία")
        String vat
) {}
