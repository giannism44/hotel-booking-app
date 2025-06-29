package gr.aueb.cf.hotelapp.dto;

public record ClientReadOnlyDTO(
        Long id,
        String username,
        String firstname,
        String lastname,
        String phone,
        String vat,
        boolean hasActiveReservation,
        int totalBookings
) {}