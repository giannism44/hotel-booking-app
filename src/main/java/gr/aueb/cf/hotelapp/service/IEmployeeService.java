package gr.aueb.cf.hotelapp.service;

import gr.aueb.cf.hotelapp.core.exceptions.EmployeeNotFoundException;
import gr.aueb.cf.hotelapp.core.exceptions.UsernameAlreadyExistsException;
import gr.aueb.cf.hotelapp.dto.EmployeeInsertDTO;
import gr.aueb.cf.hotelapp.dto.EmployeeReadOnlyDTO;
import gr.aueb.cf.hotelapp.dto.EmployeeUpdateDTO;

import java.util.List;

public interface IEmployeeService {
    EmployeeReadOnlyDTO insertEmployee(EmployeeInsertDTO dto)
        throws UsernameAlreadyExistsException;
    EmployeeReadOnlyDTO updateEmployee(EmployeeUpdateDTO dto)
        throws EmployeeNotFoundException, UsernameAlreadyExistsException;
    void deleteEmployee(Long id)
            throws EmployeeNotFoundException;
    List<EmployeeReadOnlyDTO> getAllEmployees();
    EmployeeReadOnlyDTO getEmployeeById(Long id)
        throws EmployeeNotFoundException;
}
