package gr.aueb.cf.hotelapp.mapper;

import gr.aueb.cf.hotelapp.core.enums.Role;
import gr.aueb.cf.hotelapp.dto.*;
import gr.aueb.cf.hotelapp.model.Employee;
import gr.aueb.cf.hotelapp.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Mapper για μετατροπή μεταξύ Employee entity και DTOs.
 * Χρησιμοποιείται για εισαγωγή, ενημέρωση και προβολή υπαλλήλων.
 */
@Component
@RequiredArgsConstructor
public class EmployeeMapper {

    private final PasswordEncoder passwordEncoder;

    public EmployeeReadOnlyDTO mapToEmployeeReadOnlyDTO(Employee employee) {
        return new EmployeeReadOnlyDTO(
                employee.getId(),
                employee.getUser().getUsername(),
                employee.getFirstname(),
                employee.getLastname()
        );
    }

    public Employee mapToEmployeeEntity(EmployeeInsertDTO dto) {
        Employee employee = new Employee();
        employee.setFirstname(dto.firstname());
        employee.setLastname(dto.lastname());

        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(Role.EMPLOYEE);

        employee.setUser(user);

        return employee;
    }

    public Employee mapToEmployeeEntity(EmployeeUpdateDTO dto) {
        Employee employee = new Employee();
        employee.setId(dto.id());
        employee.setFirstname(dto.firstname());
        employee.setLastname(dto.lastname());

        return employee;
    }
}
