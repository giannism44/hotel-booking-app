package gr.aueb.cf.hotelapp.dto;

import gr.aueb.cf.hotelapp.core.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserInsertDTO(
        @Email(message = "Το username πρέπει να είναι έγκυρο email")
        String username,

        @Pattern(regexp = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\d)(?=.*?[@#$!%&*]).{8,}$",
                message = "Εισάγεται έγκυρο κωδικό")
        String password,

        @NotNull(message = "Ο ρόλος είναι υποχρεωτικός")
        Role role
) {}
