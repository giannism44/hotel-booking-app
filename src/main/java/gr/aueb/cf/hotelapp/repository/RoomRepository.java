package gr.aueb.cf.hotelapp.repository;

import gr.aueb.cf.hotelapp.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
