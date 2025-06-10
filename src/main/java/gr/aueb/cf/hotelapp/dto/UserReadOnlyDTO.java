package gr.aueb.cf.hotelapp.dto;

import gr.aueb.cf.hotelapp.core.enums.Role;

public record UserReadOnlyDTO(
        Long id,
        String username,
        Role role
) {}
