package gr.aueb.cf.hotelapp.mapper;

import gr.aueb.cf.hotelapp.dto.RoomReadOnlyDTO;
import gr.aueb.cf.hotelapp.dto.RoomUpdateDTO;
import gr.aueb.cf.hotelapp.model.Room;
import org.springframework.stereotype.Component;

/**
 * Mapper για μετατροπή μεταξύ Room entity και DTOs.
 * Χρησιμοποιείται για προβολή και ενημέρωση δωματίων.
 */
@Component
public class RoomMapper {

    public RoomReadOnlyDTO mapToRoomReadOnlyDTO(Room room) {
        return new RoomReadOnlyDTO(
                room.getId(),
                room.getRoomNumber(),
                room.getRoomType(),
                room.getPrice()
        );
    }

    public Room mapToRoomEntity(RoomUpdateDTO dto) {
        Room room = new Room();
        room.setId(dto.id());
        room.setRoomType(dto.roomType());
        room.setPrice(dto.price());
        return room;
    }
}
