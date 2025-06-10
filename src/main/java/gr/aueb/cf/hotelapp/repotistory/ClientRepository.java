package gr.aueb.cf.hotelapp.repotistory;

import gr.aueb.cf.hotelapp.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
