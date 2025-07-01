package gr.aueb.cf.hotelapp.dto;

import gr.aueb.cf.hotelapp.core.enums.RoomType;
import jakarta.validation.constraints.*;

/**
 * DTO για ενημέρωση δωματίου.
 * Περιλαμβάνει τύπο, τιμή και διαθεσιμότητα.
 */
public record RoomUpdateDTO(
        Long id,

        @NotNull(message = "Ο τύπος δωματίου είναι υποχρεωτικός")
        RoomType roomType,

        @NotNull(message = "Η τιμή είναι υποχρεωτική")
        @DecimalMin(value = "0.0", inclusive = false, message = "Η τιμή πρέπει να είναι θετική")
        Double price
) {}

