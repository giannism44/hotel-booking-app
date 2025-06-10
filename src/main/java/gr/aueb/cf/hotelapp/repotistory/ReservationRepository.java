package gr.aueb.cf.hotelapp.repotistory;

import gr.aueb.cf.hotelapp.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
