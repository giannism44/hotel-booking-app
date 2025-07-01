package gr.aueb.cf.hotelapp.service;

import gr.aueb.cf.hotelapp.core.exceptions.RoomNotFoundException;
import gr.aueb.cf.hotelapp.dto.RoomReadOnlyDTO;
import gr.aueb.cf.hotelapp.dto.RoomUpdateDTO;

import java.time.LocalDate;
import java.util.List;

public interface IRoomService {
    RoomReadOnlyDTO updateRoom(RoomUpdateDTO dto)
            throws RoomNotFoundException;
    List<RoomReadOnlyDTO> getAllRooms();
    RoomReadOnlyDTO getRoomById(Long id)
            throws RoomNotFoundException ;
    List<RoomReadOnlyDTO> getAvailableRoomsForDates(LocalDate checkIn, LocalDate checkOut);

}
