package gr.aueb.cf.hotelapp.repository;

import gr.aueb.cf.hotelapp.model.Reservation;
import gr.aueb.cf.hotelapp.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

/**
 * Repository για οντότητα Reservation.
 * Περιλαμβάνει μέθοδο για έλεγχο επικαλυπτόμενων κρατήσεων.
 */
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    /**
     * Επιστρέφει true αν υπάρχει ήδη κράτηση για το δωμάτιο
     * που επικαλύπτει το δοσμένο χρονικό διάστημα.
     */
    boolean existsByRoomAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(
            Room room, LocalDate checkOut, LocalDate checkIn);
}
