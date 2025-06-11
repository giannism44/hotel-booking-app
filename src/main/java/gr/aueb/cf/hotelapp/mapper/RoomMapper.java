package gr.aueb.cf.hotelapp.mapper;

import gr.aueb.cf.hotelapp.dto.RoomInsertDTO;
import gr.aueb.cf.hotelapp.dto.RoomReadOnlyDTO;
import gr.aueb.cf.hotelapp.dto.RoomUpdateDTO;
import gr.aueb.cf.hotelapp.model.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {

    public RoomReadOnlyDTO mapToRoomReadOnlyDTO(Room room) {
        return new RoomReadOnlyDTO(
                room.getId(),
                room.getRoomNumber(),
                room.getRoomType(),
                room.getPrice(),
                room.getIsAvailable()
        );
    }

    public Room mapToRoomEntity(RoomInsertDTO dto) {
        Room room = new Room();
        room.setRoomNumber(dto.roomNumber());
        room.setRoomType(dto.roomType());
        room.setPrice(dto.price());
        room.setIsAvailable(dto.isAvailable());
        return room;
    }

    public Room mapToRoomEntity(RoomUpdateDTO dto) {
        Room room = new Room();
        room.setId(dto.id());
        room.setRoomType(dto.roomType());
        room.setPrice(dto.price());
        room.setIsAvailable(dto.isAvailable());
        return room;
    }
}
