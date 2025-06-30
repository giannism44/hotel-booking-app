package gr.aueb.cf.hotelapp.mapper;

import gr.aueb.cf.hotelapp.core.enums.ReservationStatus;
import gr.aueb.cf.hotelapp.core.enums.Role;
import gr.aueb.cf.hotelapp.dto.ClientInsertDTO;
import gr.aueb.cf.hotelapp.dto.ClientReadOnlyDTO;
import gr.aueb.cf.hotelapp.dto.ClientUpdateDTO;
import gr.aueb.cf.hotelapp.model.Client;
import gr.aueb.cf.hotelapp.model.Reservation;
import gr.aueb.cf.hotelapp.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * Mapper για μετατροπή μεταξύ Client entity και DTOs.
 * Περιλαμβάνει map για εισαγωγή, ενημέρωση και προβολή.
 */
@Component
@RequiredArgsConstructor
public class ClientMapper {

    private final PasswordEncoder passwordEncoder;

    public static ClientReadOnlyDTO mapToClientReadOnlyDTO(Client client) {
        List<Reservation> reservations = client.getReservations();

        boolean hasActiveReservation = reservations != null &&
                reservations.stream()
                        .anyMatch(r ->
                                r.getStatus() == ReservationStatus.CONFIRMED &&
                                        r.getCheckOut().isAfter(LocalDate.now())
                        );

        int totalBookings = (client.getClientStatus() != null)
                ? client.getClientStatus().getTotalBookings()
                : 0;

        return new ClientReadOnlyDTO(
                client.getId(),
                client.getUser() != null ? client.getUser().getUsername() : "",
                client.getFirstname(),
                client.getLastname(),
                client.getPhone(),
                client.getVat(),
                hasActiveReservation,
                totalBookings
        );
    }


    public Client mapToClientEntity(ClientInsertDTO dto) {
        Client client = new Client();
        client.setFirstname(dto.firstname());
        client.setLastname(dto.lastname());
        client.setPhone(dto.phone());
        client.setVat(dto.vat());

        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(Role.CLIENT);

        client.setUser(user);

        return client;
    }

    public Client mapToClientEntity(ClientUpdateDTO dto) {
        Client client = new Client();
        client.setId(dto.id());
        client.setFirstname(dto.firstname());
        client.setLastname(dto.lastname());
        client.setPhone(dto.phone());
        client.setVat(dto.vat());

        return client;
    }

}
