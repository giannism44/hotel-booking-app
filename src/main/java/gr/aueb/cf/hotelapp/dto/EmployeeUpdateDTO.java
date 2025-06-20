package gr.aueb.cf.hotelapp.dto;

import jakarta.validation.constraints.NotEmpty;

/**
 * DTO για ενημέρωση στοιχείων υπαλλήλου.
 * Χρησιμοποιείται για αλλαγή ονόματος και επιθέτου.
 */
public record EmployeeUpdateDTO(
        Long id,

        @NotEmpty(message = "Το όνομα είναι υποχρεωτικό")
        String firstname,

        @NotEmpty(message = "Το επίθετο είναι υποχρεωτικό")
        String lastname
) {}
