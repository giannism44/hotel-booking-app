package gr.aueb.cf.hotelapp.repository;

import gr.aueb.cf.hotelapp.model.Employee;
import gr.aueb.cf.hotelapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUser(User user);
}
