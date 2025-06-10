package gr.aueb.cf.hotelapp.repotistory;

import gr.aueb.cf.hotelapp.core.enums.Role;
import gr.aueb.cf.hotelapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Optional<User> findAllByRole(Role role);
}
