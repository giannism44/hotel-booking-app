package gr.aueb.cf.hotelapp.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * DTO για εισαγωγή νέας κράτησης.
 * Περιλαμβάνει ημερομηνίες, δωμάτιο και πελάτη με απαραίτητο validation.
 */
public record ReservationInsertDTO(
        @NotNull(message = "Η ημερομηνία check-in είναι υποχρεωτική")
        @FutureOrPresent(message = "Η ημερομηνία check-in πρέπει να είναι στο μέλλον")
        LocalDate checkIn,

        @NotNull(message = "Η ημερομηνία check-out είναι υποχρεωτική")
        @FutureOrPresent(message = "Η ημερομηνία check-out πρέπει να είναι στο μέλλον")
        LocalDate checkOut,

        @NotNull(message = "Ο τύπος δωματίου είναι υποχρεωτικός")
        Long roomId,

        @NotNull(message = "Ο πελάτης είναι υποχρεωτικός")
        Long clientId
) {}
