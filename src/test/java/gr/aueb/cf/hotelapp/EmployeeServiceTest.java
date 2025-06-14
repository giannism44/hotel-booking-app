package gr.aueb.cf.hotelapp;

import gr.aueb.cf.hotelapp.core.exceptions.EmployeeNotFoundException;
import gr.aueb.cf.hotelapp.dto.EmployeeInsertDTO;
import gr.aueb.cf.hotelapp.dto.EmployeeReadOnlyDTO;
import gr.aueb.cf.hotelapp.dto.EmployeeUpdateDTO;
import gr.aueb.cf.hotelapp.repository.EmployeeRepository;
import gr.aueb.cf.hotelapp.repository.UserRepository;
import gr.aueb.cf.hotelapp.service.IEmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = HotelBookingSystemApplication.class)
public class EmployeeServiceTest {

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() throws Exception {
        if (userRepository.findByUsername("employee@example.com").isEmpty()) {
            EmployeeInsertDTO dto = new EmployeeInsertDTO(
                    "employee@example.com",
                    "Password1!",
                    "Test",
                    "User"
            );
            employeeService.insertEmployee(dto);
        }
    }
    @Test
    public void insertEmployee_successful() throws Exception {
        EmployeeInsertDTO dto = new EmployeeInsertDTO(
                "john@example.com",
                "Password1!",
                "John",
                "Doe"
        );

        EmployeeReadOnlyDTO result = employeeService.insertEmployee(dto);

        System.out.println("Firstname: " + result.firstname());
        System.out.println("Lastname: " + result.lastname());
        System.out.println("Username: " + result.username());

        assertNotNull(result);
        assertEquals("John", result.firstname());
        assertEquals("Doe", result.lastname());
        assertEquals("john@example.com", result.username());

        employeeRepository.deleteById(result.id());
        userRepository.findByUsername("john@example.com").ifPresent(user -> userRepository.delete(user));
    }

    @Test
    public void updateEmployee_successful_alternate() throws Exception {
        EmployeeInsertDTO insertDTO = new EmployeeInsertDTO(
                "uniqueupdate@example.com",
                "Password1!",
                "Maria",
                "Papadaki"
        );
        EmployeeReadOnlyDTO inserted = employeeService.insertEmployee(insertDTO);

        EmployeeUpdateDTO updateDTO = new EmployeeUpdateDTO(
                inserted.id(),
                "Eleni",
                "Kritikou"
        );

        EmployeeReadOnlyDTO updated = employeeService.updateEmployee(updateDTO);

        assertNotNull(updated);
        assertEquals("Eleni", updated.firstname());
        assertEquals("Kritikou", updated.lastname());
        assertEquals("uniqueupdate@example.com", updated.username());

        employeeRepository.deleteById(updated.id());
        userRepository.findByUsername("uniqueupdate@example.com")
                .ifPresent(user -> userRepository.delete(user));
    }


    @Test
    public void getAllEmployees_returnsListWithEmployees() throws Exception {
        EmployeeInsertDTO dto = new EmployeeInsertDTO(
                "listtest@example.com",
                "Password1!",
                "TestFirst",
                "TestLast"
        );

        EmployeeReadOnlyDTO inserted = employeeService.insertEmployee(dto);

        List<EmployeeReadOnlyDTO> employees = employeeService.getAllEmployees();

        assertNotNull(employees);
        assertTrue(employees.stream()
                .anyMatch(e -> e.username().equals("listtest@example.com")));

        employeeRepository.deleteById(inserted.id());
        userRepository.findByUsername("listtest@example.com")
                .ifPresent(user -> userRepository.delete(user));
    }

    @Test
    public void deleteEmployee_successful() throws Exception {
        EmployeeInsertDTO dto = new EmployeeInsertDTO(
                "delete_test@example.com",
                "Password1!",
                "DeleteFirst",
                "DeleteLast"
        );
        EmployeeReadOnlyDTO inserted = employeeService.insertEmployee(dto);

        employeeService.deleteEmployee(inserted.id());

        assertFalse(employeeRepository.findById(inserted.id()).isPresent());
        assertFalse(userRepository.findByUsername("delete_test@example.com").isPresent());
    }

    @Test
    public void getEmployeeById_throwsExceptionIfNotFound() {
        Long invalidId = 999L;

        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.getEmployeeById(invalidId);
        });

        String expectedMessage = "Ο υπάλληλος με id " + invalidId + " δεν βρέθηκε.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getEmployeeById_successful() throws Exception {
        EmployeeInsertDTO dto = new EmployeeInsertDTO(
                "testget@example.com",
                "Password1!",
                "Maria",
                "Papadopoulou"
        );
        EmployeeReadOnlyDTO inserted = employeeService.insertEmployee(dto);

        EmployeeReadOnlyDTO result = employeeService.getEmployeeById(inserted.id());

        assertNotNull(result);
        assertEquals("Maria", result.firstname());
        assertEquals("Papadopoulou", result.lastname());
        assertEquals("testget@example.com", result.username());

        employeeRepository.deleteById(inserted.id());
        userRepository.findByUsername("testget@example.com").ifPresent(user -> userRepository.delete(user));
    }



    @Test
    public void getAllEmployees_returnsListOfEmployees() throws Exception {
        EmployeeInsertDTO dto = new EmployeeInsertDTO(
                "getall@example.com",
                "Password1!",
                "George",
                "Papadopoulos"
        );

        EmployeeReadOnlyDTO savedEmployee = employeeService.insertEmployee(dto);

        List<EmployeeReadOnlyDTO> employees = employeeService.getAllEmployees();

        assertNotNull(employees);
        assertFalse(employees.isEmpty());

        boolean found = employees.stream()
                .anyMatch(e -> e.id().equals(savedEmployee.id())
                        && e.firstname().equals("George")
                        && e.lastname().equals("Papadopoulos")
                        && e.username().equals("getall@example.com"));

        assertTrue(found);

        employeeRepository.deleteById(savedEmployee.id());
        userRepository.findByUsername("getall@example.com").ifPresent(user -> userRepository.delete(user));
    }
}
