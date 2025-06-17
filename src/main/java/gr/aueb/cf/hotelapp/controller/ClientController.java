package gr.aueb.cf.hotelapp.controller;

import gr.aueb.cf.hotelapp.core.exceptions.ClientNotFoundException;
import gr.aueb.cf.hotelapp.core.exceptions.UsernameAlreadyExistsException;
import gr.aueb.cf.hotelapp.dto.ClientInsertDTO;
import gr.aueb.cf.hotelapp.dto.ClientReadOnlyDTO;
import gr.aueb.cf.hotelapp.dto.ClientUpdateDTO;
import gr.aueb.cf.hotelapp.mapper.ClientMapper;
import gr.aueb.cf.hotelapp.service.IClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/hotel")
@RequiredArgsConstructor
public class ClientController {

    private final ClientMapper clientMapper;
    private final IClientService clientService;

    @GetMapping("/clients/insert")
    public String getClientForm(Model model){
        model.addAttribute("clientInsertDTO",
                new ClientInsertDTO(null, null, null, null, null, null));
        return  "client-form";
    }

    @PostMapping("/clients/insert")
    public String insertClient(@Valid @ModelAttribute("clientInsertDTO") ClientInsertDTO clientInsertDTO,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "client-form";
        }

        try {
            ClientReadOnlyDTO savedClient = clientService.insertClient(clientInsertDTO);
            redirectAttributes.addFlashAttribute("client", savedClient);
            return "redirect:/hotel/clients";
        } catch (UsernameAlreadyExistsException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "client-insert";
        }
    }
}
