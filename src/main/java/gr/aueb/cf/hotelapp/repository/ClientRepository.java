package gr.aueb.cf.hotelapp.repository;

import gr.aueb.cf.hotelapp.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByVat(String vat);
    Optional<Client> findByUserUsername(String username);
    Optional<Client> findByPhone(String phone);
}
