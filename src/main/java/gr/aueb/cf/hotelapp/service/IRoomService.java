package gr.aueb.cf.hotelapp.service;

import gr.aueb.cf.hotelapp.core.exceptions.RoomNotFoundException;
import gr.aueb.cf.hotelapp.core.exceptions.RoomNumberAlreadyExistsException;
import gr.aueb.cf.hotelapp.dto.RoomInsertDTO;
import gr.aueb.cf.hotelapp.dto.RoomReadOnlyDTO;
import gr.aueb.cf.hotelapp.dto.RoomUpdateDTO;

import java.util.List;

public interface IRoomService {
    RoomReadOnlyDTO insertRoom(RoomInsertDTO dto)
            throws RoomNumberAlreadyExistsException;
    RoomReadOnlyDTO updateRoom(RoomUpdateDTO dto)
            throws RoomNotFoundException;
    void deleteRoom(Long id)
            throws RoomNotFoundException ;
    List<RoomReadOnlyDTO> getAllRooms();
    RoomReadOnlyDTO getRoomById(Long id)
            throws RoomNotFoundException ;
}
