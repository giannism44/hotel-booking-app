package gr.aueb.cf.hotelapp.service;

import gr.aueb.cf.hotelapp.core.exceptions.EmployeeNotFoundException;
import gr.aueb.cf.hotelapp.core.exceptions.UsernameAlreadyExistsException;
import gr.aueb.cf.hotelapp.dto.EmployeeInsertDTO;
import gr.aueb.cf.hotelapp.dto.EmployeeReadOnlyDTO;
import gr.aueb.cf.hotelapp.dto.EmployeeUpdateDTO;
import gr.aueb.cf.hotelapp.mapper.EmployeeMapper;
import gr.aueb.cf.hotelapp.model.Employee;
import gr.aueb.cf.hotelapp.repository.EmployeeRepository;
import gr.aueb.cf.hotelapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService{

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public EmployeeReadOnlyDTO insertEmployee(EmployeeInsertDTO dto)
            throws UsernameAlreadyExistsException {

        if (userRepository.findByUsername(dto.username()).isPresent()) {
            throw new UsernameAlreadyExistsException("User", "Ο χρήστης με username: " + dto.username() + " υπάρχει ήδη");
        }

        Employee employee = employeeMapper.mapToEmployeeEntity(dto);
        Employee savedEmployee = employeeRepository.save(employee);

        return employeeMapper.mapToEmployeeReadOnlyDTO(savedEmployee);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public EmployeeReadOnlyDTO updateEmployee(EmployeeUpdateDTO dto)
            throws EmployeeNotFoundException {

        Employee existingEmployee = employeeRepository.findById(dto.id())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee", "Ο υπάλληλος με id " + dto.id() + " δεν βρέθηκε."));

        existingEmployee.setFirstname(dto.firstname());
        existingEmployee.setLastname(dto.lastname());

        Employee updatedEmployee = employeeRepository.save(existingEmployee);

        return employeeMapper.mapToEmployeeReadOnlyDTO(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long id)
            throws EmployeeNotFoundException {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee", "Ο υπάλληλος με id " + id + " δεν βρέθηκε."));

        employeeRepository.delete(employee);
    }

    @Override
    public List<EmployeeReadOnlyDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::mapToEmployeeReadOnlyDTO)
                .toList();
    }

    @Override
    public EmployeeReadOnlyDTO getEmployeeById(Long id) throws EmployeeNotFoundException {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee", "Ο υπάλληλος με id " + id + " δεν βρέθηκε."));

        return employeeMapper.mapToEmployeeReadOnlyDTO(employee);
    }
}
