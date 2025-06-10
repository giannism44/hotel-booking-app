package gr.aueb.cf.hotelapp.dto;

public record EmployeeReadOnlyDTO(
        Long id,
        String username,
        String firstname,
        String lastname
) {}
