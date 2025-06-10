package gr.aueb.cf.hotelapp.dto;

import gr.aueb.cf.hotelapp.core.enums.RoomType;

public record RoomReadOnlyDTO(
        Long id,
        String roomNumber,
        RoomType roomType,
        Double price,
        Boolean isAvailable
) {}

