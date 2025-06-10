package gr.aueb.cf.hotelapp.dto;

import gr.aueb.cf.hotelapp.core.enums.ReservationStatus;

import java.time.LocalDate;

public record ReservationReadOnlyDTO(
        Long id,
        LocalDate checkIn,
        LocalDate checkOut,
        ReservationStatus status,
        String roomNumber,
        String clientFullName
) {
}
