package gr.aueb.cf.hotelapp.dto;

import gr.aueb.cf.hotelapp.core.enums.RoomType;
import jakarta.validation.constraints.*;

public record RoomInsertDTO(
        @NotBlank(message = "Ο αριθμός δωματίου είναι υποχρεωτικός")
        String roomNumber,

        @NotNull(message = "Ο τύπος δωματίου είναι υποχρεωτικός")
        RoomType roomType,

        @NotNull(message = "Η τιμή είναι υποχρεωτική")
        @DecimalMin(value = "0.0", inclusive = false, message = "Η τιμή πρέπει να είναι θετική")
        Double price,

        Boolean isAvailable
) {}
