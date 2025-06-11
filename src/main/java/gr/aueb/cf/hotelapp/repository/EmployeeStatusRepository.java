package gr.aueb.cf.hotelapp.repository;

import gr.aueb.cf.hotelapp.model.EmployeeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeStatusRepository extends JpaRepository<EmployeeStatus, Long> {
}
