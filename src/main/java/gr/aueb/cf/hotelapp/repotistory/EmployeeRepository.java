package gr.aueb.cf.hotelapp.repotistory;

import gr.aueb.cf.hotelapp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
