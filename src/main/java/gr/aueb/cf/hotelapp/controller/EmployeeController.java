package gr.aueb.cf.hotelapp.controller;

import gr.aueb.cf.hotelapp.core.exceptions.EmployeeNotFoundException;
import gr.aueb.cf.hotelapp.core.exceptions.UsernameAlreadyExistsException;
import gr.aueb.cf.hotelapp.dto.ClientInsertDTO;
import gr.aueb.cf.hotelapp.dto.EmployeeInsertDTO;
import gr.aueb.cf.hotelapp.dto.EmployeeReadOnlyDTO;
import gr.aueb.cf.hotelapp.dto.EmployeeUpdateDTO;
import gr.aueb.cf.hotelapp.mapper.EmployeeMapper;
import gr.aueb.cf.hotelapp.service.IEmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        return "employee-form";
    }

    @PostMapping("/insert")
    public String insertEmployee(@Valid @ModelAttribute("employeeInsertDTO") EmployeeInsertDTO employeeInsertDTO,
                                 BindingResult bindingResult,
                                 Model model,
                                 RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()) {
            return "employee-form";
        }

        try {
            EmployeeReadOnlyDTO savedEmployee = employeeService.insertEmployee(employeeInsertDTO);
            redirectAttributes.addFlashAttribute("employee", savedEmployee);
            return "redirect:/hotel/employees";
        }catch (UsernameAlreadyExistsException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "employee-form";
        }
    }
}
