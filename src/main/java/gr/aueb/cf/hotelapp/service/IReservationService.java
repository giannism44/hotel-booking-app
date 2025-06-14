package gr.aueb.cf.hotelapp.service;

import gr.aueb.cf.hotelapp.core.exceptions.ClientNotFoundException;
import gr.aueb.cf.hotelapp.core.exceptions.ReservationNotFoundException;
import gr.aueb.cf.hotelapp.core.exceptions.RoomNotAvailableException;
import gr.aueb.cf.hotelapp.core.exceptions.UserNotFoundException;
import gr.aueb.cf.hotelapp.dto.ReservationInsertDTO;
import gr.aueb.cf.hotelapp.dto.ReservationReadOnlyDTO;

import java.util.List;

public interface IReservationService {
    ReservationReadOnlyDTO insertReservation(ReservationInsertDTO dto)
            throws RoomNotAvailableException, ClientNotFoundException, UserNotFoundException;

    void deleteReservation(Long id)
            throws ReservationNotFoundException;

    List<ReservationReadOnlyDTO> getAllReservations();

    ReservationReadOnlyDTO getReservationById(Long id)
            throws ReservationNotFoundException;
}
