package gr.aueb.cf.hotelapp.repotistory;

import gr.aueb.cf.hotelapp.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
