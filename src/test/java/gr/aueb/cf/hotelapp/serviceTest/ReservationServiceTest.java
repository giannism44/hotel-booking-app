package gr.aueb.cf.hotelapp.serviceTest;

import gr.aueb.cf.hotelapp.HotelBookingSystemApplication;
import gr.aueb.cf.hotelapp.core.enums.ReservationStatus;
import gr.aueb.cf.hotelapp.core.enums.Role;
import gr.aueb.cf.hotelapp.core.enums.RoomType;
import gr.aueb.cf.hotelapp.core.exceptions.RoomNotAvailableException;
import gr.aueb.cf.hotelapp.dto.ClientInsertDTO;
import gr.aueb.cf.hotelapp.dto.ClientReadOnlyDTO;
import gr.aueb.cf.hotelapp.dto.ReservationInsertDTO;
import gr.aueb.cf.hotelapp.dto.ReservationReadOnlyDTO;
import gr.aueb.cf.hotelapp.model.Room;
import gr.aueb.cf.hotelapp.model.User;
import gr.aueb.cf.hotelapp.repository.*;
import gr.aueb.cf.hotelapp.service.IClientService;
import gr.aueb.cf.hotelapp.service.IReservationService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Commit
@SpringBootTest(classes = HotelBookingSystemApplication.class)
public class ReservationServiceTest {

    @Autowired private IReservationService reservationService;
    @Autowired private IClientService clientService;
    @Autowired private ClientRepository clientRepository;
    @Autowired private RoomRepository roomRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ReservationRepository reservationRepository;
    @Autowired private DataSource dataSource;

    private Long clientId;
    private Long roomId;

    @BeforeEach
    public void setup() throws Exception {
        String randomEmail = "client_" + UUID.randomUUID() + "@test.com";
        ClientInsertDTO clientDTO = new ClientInsertDTO(
                randomEmail, "Password1!", "Nikos", "Kostopoulos", "6999999999", "993446789");
        ClientReadOnlyDTO client = clientService.insertClient(clientDTO);
        clientId = client.id();

        Room room = new Room();
        room.setRoomNumber("101A_" + UUID.randomUUID().toString().substring(0, 5));
        room.setRoomType(RoomType.SINGLE);
        room.setPrice(85.0);
        room.setIsAvailable(true);
        room = roomRepository.save(room);
        roomId = room.getId();

        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("Password1!");
            admin.setRole(Role.EMPLOYEE);
            userRepository.save(admin);
        }
    }

    @Test
    public void insertReservation_successful() throws Exception {
        LocalDate checkIn = LocalDate.now().plusDays(1);
        LocalDate checkOut = LocalDate.now().plusDays(3);

        ReservationInsertDTO dto = new ReservationInsertDTO(checkIn, checkOut, roomId, clientId);
        ReservationReadOnlyDTO saved = reservationService.insertReservation(dto);

        assertNotNull(saved);
        assertEquals(checkIn, saved.checkIn());
        assertEquals(checkOut, saved.checkOut());
        assertEquals(ReservationStatus.CONFIRMED, saved.status());
        assertEquals("Nikos Kostopoulos", saved.clientFullName());

        Room room = roomRepository.findById(roomId).orElseThrow();
        assertFalse(room.getIsAvailable());
    }

    @Test
    public void insertReservation_failsIfRoomAlreadyBooked() throws Exception {
        LocalDate checkIn = LocalDate.now().plusDays(1);
        LocalDate checkOut = LocalDate.now().plusDays(3);

        ReservationInsertDTO dto = new ReservationInsertDTO(checkIn, checkOut, roomId, clientId);
        reservationService.insertReservation(dto);

        assertThrows(RoomNotAvailableException.class, () -> {
            reservationService.insertReservation(dto);
        });
    }


    @Test
    public void deleteReservation_setsRoomAvailable() throws Exception {
        LocalDate checkIn = LocalDate.now().plusDays(1);
        LocalDate checkOut = LocalDate.now().plusDays(3);

        ReservationInsertDTO dto = new ReservationInsertDTO(checkIn, checkOut, roomId, clientId);
        ReservationReadOnlyDTO reservation = reservationService.insertReservation(dto);

        reservationService.deleteReservation(reservation.id());

        Room room = roomRepository.findById(roomId).orElseThrow();
        assertTrue(room.getIsAvailable());
    }
}
