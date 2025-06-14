package gr.aueb.cf.hotelapp.repository;

import gr.aueb.cf.hotelapp.model.Reservation;
import gr.aueb.cf.hotelapp.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByRoomAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(
            Room room, LocalDate checkOut, LocalDate checkIn);
}
