package gr.aueb.cf.hotelapp.repository;

import gr.aueb.cf.hotelapp.model.ClientStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientStatusRepository extends JpaRepository<ClientStatus,Long> {
}
