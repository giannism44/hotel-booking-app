package gr.aueb.cf.hotelapp.service;

import gr.aueb.cf.hotelapp.core.exceptions.ClientNotFoundException;
import gr.aueb.cf.hotelapp.core.exceptions.ReservationNotFoundException;
import gr.aueb.cf.hotelapp.core.exceptions.RoomNotAvailableException;
import gr.aueb.cf.hotelapp.core.exceptions.UserNotFoundException;
import gr.aueb.cf.hotelapp.dto.ReservationInsertDTO;
import gr.aueb.cf.hotelapp.dto.ReservationReadOnlyDTO;
import gr.aueb.cf.hotelapp.mapper.ReservationMapper;
import gr.aueb.cf.hotelapp.model.*;
import gr.aueb.cf.hotelapp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final ClientStatusRepository clientStatusRepository;
    private final EmployeeStatusRepository employeeStatusRepository;
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final ReservationMapper reservationMapper;

    @Override
    public ReservationReadOnlyDTO insertReservation(ReservationInsertDTO dto)
            throws RoomNotAvailableException, ClientNotFoundException, UserNotFoundException {

        Room room = roomRepository.findById(dto.roomId())
                .orElseThrow(() -> new RoomNotAvailableException("Room", "Το δωμάτιο με id: " + dto.roomId() + " δεν είναι διαθέσιμο"));

        if (!room.getIsAvailable()) {
            throw new RoomNotAvailableException("Room", "Το δωμάτιο με id: " + room.getId() + " δεν είναι διαθέσιμο");
        }

        Client client = clientRepository.findById(dto.clientId())
                .orElseThrow(() -> new ClientNotFoundException("Client", "Ο πελάτης δεν βρέθηκε."));

        User user = userRepository.findByUsername("admin")
                .orElseThrow(() -> new UserNotFoundException("User", "Ο χρήστης δεν βρέθηκε."));

        boolean isBooked = reservationRepository
                .existsByRoomAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(
                        room, dto.checkOut(), dto.checkIn());

        if (isBooked) {
            throw new RoomNotAvailableException("Room",
                    "Το δωμάτιο είναι ήδη κρατημένο για τις ημερομηνίες που επιλέξατε.");
        }

        Reservation reservation = reservationMapper.mapToReservationEntity(dto, room, client, user);
        Reservation saved = reservationRepository.save(reservation);

        room.setIsAvailable(false);
        roomRepository.save(room);

        ClientStatus clientStatus = clientStatusRepository.findById(client.getId())
                .orElseGet(() -> {
                    ClientStatus cs = new ClientStatus();
                    cs.setClient(client);
                    cs.setTotalBookings(0);
                    cs.setDiscountActive(false);
                    return cs;
                });

        clientStatus.setTotalBookings(clientStatus.getTotalBookings() + 1);
        if (clientStatus.getTotalBookings() >= 3) {
            clientStatus.setDiscountActive(true);
        }
        clientStatusRepository.save(clientStatus);

        employeeRepository.findByUser(user).ifPresent(employee -> {
            EmployeeStatus status = employeeStatusRepository.findById(employee.getId())
                    .orElseGet(() -> {
                        EmployeeStatus es = new EmployeeStatus();
                        es.setEmployee(employee);
                        es.setTotalBookings(0);
                        es.setBonusAwarded(false);
                        return es;
                    });

            status.setTotalBookings(status.getTotalBookings() + 1);
            if (status.getTotalBookings() >= 5) {
                status.setBonusAwarded(true);
            }

            employeeStatusRepository.save(status);
        });

        return reservationMapper.mapToReservationReadOnlyDTO(saved);
    }

    @Override
    public void deleteReservation(Long id) throws ReservationNotFoundException {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation", "Η κράτηση με id: " + id + " δεν βρέθηκε"));

        Room room = reservation.getRoom();
        room.setIsAvailable(true);
        roomRepository.save(room);

        reservationRepository.deleteById(id);
    }

    @Override
    public List<ReservationReadOnlyDTO> getAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(reservationMapper::mapToReservationReadOnlyDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReservationReadOnlyDTO getReservationById(Long id) throws ReservationNotFoundException {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation", "Η κράτηση με id: " + id + " δεν βρέθηκε"));
        return reservationMapper.mapToReservationReadOnlyDTO(reservation);
    }
}
