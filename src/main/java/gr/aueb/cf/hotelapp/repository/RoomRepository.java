package gr.aueb.cf.hotelapp.repository;

import gr.aueb.cf.hotelapp.core.enums.RoomType;
import gr.aueb.cf.hotelapp.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
