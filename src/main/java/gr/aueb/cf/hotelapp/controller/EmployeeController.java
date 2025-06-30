package gr.aueb.cf.hotelapp.controller;

import gr.aueb.cf.hotelapp.core.exceptions.EmployeeNotFoundException;
import gr.aueb.cf.hotelapp.core.exceptions.UsernameAlreadyExistsException;
import gr.aueb.cf.hotelapp.dto.*;
import gr.aueb.cf.hotelapp.mapper.EmployeeMapper;
import gr.aueb.cf.hotelapp.service.IEmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/hotel/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeMapper employeeMapper;
    private final IEmployeeService employeeService;

    @GetMapping("/insert")
    public String getEmployeeForm(Model model){
        model.addAttribute("employeeInsertDTO" ,
                new EmployeeInsertDTO(null,null,null,null));
        return "employee/employee-form";
    }

    @PostMapping("/insert")
    public String insertEmployee(@Valid @ModelAttribute("employeeInsertDTO") EmployeeInsertDTO employeeInsertDTO,
                                 BindingResult bindingResult,
                                 Model model,
                                 RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()) {
            return "employee/employee-form";
        }

        try {
            EmployeeReadOnlyDTO savedEmployee = employeeService.insertEmployee(employeeInsertDTO);
            redirectAttributes.addFlashAttribute("employee", savedEmployee);
            redirectAttributes.addFlashAttribute("successMessage", "Η εγγραφή ολοκληρώθηκε επιτυχώς!");
            return "redirect:/hotel/employees/registered";
        }catch (UsernameAlreadyExistsException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "employee/employee-form";
        }
    }

    @GetMapping("/registered")
    public String showEmployeeRegistered(Model model) {
        System.out.println("Success Message: " + model.getAttribute("successMessage"));
        model.addAttribute("returnUrl", "/login");
        return "pages/registration-success";
    }


    @GetMapping("/update/{id}")
    public String getUpdateEmployeeForm(@PathVariable Long id, Model model)
            throws EmployeeNotFoundException {
        EmployeeReadOnlyDTO readOnlyDTO = employeeService.getEmployeeById(id);

        EmployeeUpdateDTO updateDTO = new EmployeeUpdateDTO(
                readOnlyDTO.id(),
                readOnlyDTO.firstname(),
                readOnlyDTO.lastname()
        );

        model.addAttribute("employeeUpdateDTO", updateDTO);
        return "employee/employee-update";
    }

    @PostMapping("/update")
    public String updateEmployee(@Valid @ModelAttribute("employeeUpdateDTO") EmployeeUpdateDTO employeeUpdateDTO,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()) {
            return "employee/employee-update";
        }

        try {
            EmployeeReadOnlyDTO updateEmployee = employeeService.updateEmployee(employeeUpdateDTO);
            redirectAttributes.addFlashAttribute("employee", updateEmployee);
            return "redirect:/hotel/employees";
        }catch (UsernameAlreadyExistsException | EmployeeNotFoundException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "employee/employee-update";
        }
    }

    @GetMapping("")
    public String getAllEmployees(Model model) {
        List<EmployeeReadOnlyDTO> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "employee/employee-List";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id,  RedirectAttributes redirectAttributes){
        try {
            employeeService.deleteEmployee(id);
            redirectAttributes.addFlashAttribute("successMessage", "Ο υπάλληλος διαγράφηκε με επιτυχία");
        }catch (EmployeeNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/hotel/employees";
    }

    @GetMapping("/{id}")
    public String getEmployeeById(@PathVariable Long id, Model model)
            throws EmployeeNotFoundException{

        EmployeeReadOnlyDTO employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "employee/employee-details";
    }
}
