package gr.aueb.cf.hotelapp.mapper;

import gr.aueb.cf.hotelapp.core.enums.ReservationStatus;
import gr.aueb.cf.hotelapp.dto.ReservationInsertDTO;
import gr.aueb.cf.hotelapp.dto.ReservationReadOnlyDTO;
import gr.aueb.cf.hotelapp.model.Client;
import gr.aueb.cf.hotelapp.model.Reservation;
import gr.aueb.cf.hotelapp.model.Room;
import gr.aueb.cf.hotelapp.model.User;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public ReservationReadOnlyDTO mapToReservationReadOnlyDTO(Reservation reservation) {
        String clientFullName = reservation.getClient().getFirstname() + " " + reservation.getClient().getLastname();

        return new ReservationReadOnlyDTO(
                reservation.getId(),
                reservation.getCheckIn(),
                reservation.getCheckOut(),
                reservation.getStatus(),
                reservation.getRoom().getRoomNumber(),
                clientFullName
        );
    }

    public Reservation mapToReservationEntity(ReservationInsertDTO dto, Room room, Client client, User createdBy) {
        Reservation reservation = new Reservation();
        reservation.setCheckIn(dto.checkIn());
        reservation.setCheckOut(dto.checkOut());
        reservation.setRoom(room);
        reservation.setClient(client);
        reservation.setCreatedBy(createdBy);
        reservation.setStatus(ReservationStatus.CONFIRMED);
        return reservation;
    }
}
