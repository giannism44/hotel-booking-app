package gr.aueb.cf.hotelapp.serviceTest;

import gr.aueb.cf.hotelapp.HotelBookingSystemApplication;
import gr.aueb.cf.hotelapp.core.exceptions.RoomNotFoundException;
import gr.aueb.cf.hotelapp.dto.RoomReadOnlyDTO;
import gr.aueb.cf.hotelapp.dto.RoomUpdateDTO;
import gr.aueb.cf.hotelapp.model.Room;
import gr.aueb.cf.hotelapp.repository.RoomRepository;
import gr.aueb.cf.hotelapp.service.IRoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = HotelBookingSystemApplication.class)
public class RoomServiceTest {

    @Autowired
    private IRoomService roomService;

    @Autowired
    private RoomRepository roomRepository;

    private Room existingRoom;

    @BeforeEach
    public void setup() {
        Optional<Room> roomOpt = roomRepository.findAll().stream().findFirst();
        assertTrue(roomOpt.isPresent(), "Δεν βρέθηκε κανένα δωμάτιο στη βάση για testing");
        existingRoom = roomOpt.get();
    }

    @Test
    public void getRoomById_successful() throws Exception {
        RoomReadOnlyDTO roomDTO = roomService.getRoomById(existingRoom.getId());

        assertNotNull(roomDTO);
        assertEquals(existingRoom.getRoomNumber(), roomDTO.roomNumber());
        assertEquals(existingRoom.getRoomType(), roomDTO.roomType());
        assertEquals(existingRoom.getPrice(), roomDTO.price());
    }

    @Test
    public void getRoomById_throwsExceptionIfNotFound() {
        Long invalidId = 99999L;

        Exception exception = assertThrows(RoomNotFoundException.class, () -> {
            roomService.getRoomById(invalidId);
        });

        assertTrue(exception.getMessage().contains("δεν βρέθηκε"));
    }

    @Test
    public void updateRoom_successful() throws Exception {
        double newPrice = existingRoom.getPrice() + 15.0;
        RoomUpdateDTO updateDTO = new RoomUpdateDTO(
                existingRoom.getId(),
                existingRoom.getRoomType(),
                newPrice,
                existingRoom.getIsAvailable()
        );

        RoomReadOnlyDTO updated = roomService.updateRoom(updateDTO);

        assertNotNull(updated);
        assertEquals(existingRoom.getId(), updated.id());
        assertEquals(newPrice, updated.price());
    }

    @Test
    public void updateRoom_throwsExceptionIfNotFound() {
        RoomUpdateDTO invalidDTO = new RoomUpdateDTO(
                99999L,
                existingRoom.getRoomType(),
                existingRoom.getPrice(),
                existingRoom.getIsAvailable()
        );

        Exception exception = assertThrows(RoomNotFoundException.class, () -> {
            roomService.updateRoom(invalidDTO);
        });

        assertTrue(exception.getMessage().contains("δεν βρέθηκε"));
    }

    @Test
    public void getAllRooms_returnsList() {
        List<RoomReadOnlyDTO> rooms = roomService.getAllRooms();

        assertNotNull(rooms);
        assertFalse(rooms.isEmpty());
    }
}
