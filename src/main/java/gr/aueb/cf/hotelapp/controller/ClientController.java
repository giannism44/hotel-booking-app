package gr.aueb.cf.hotelapp.controller;

import gr.aueb.cf.hotelapp.core.exceptions.ClientHasReservationsException;
import gr.aueb.cf.hotelapp.core.exceptions.ClientNotFoundException;
import gr.aueb.cf.hotelapp.core.exceptions.UsernameAlreadyExistsException;
import gr.aueb.cf.hotelapp.dto.ClientInsertDTO;
import gr.aueb.cf.hotelapp.dto.ClientReadOnlyDTO;
import gr.aueb.cf.hotelapp.dto.ClientUpdateDTO;
import gr.aueb.cf.hotelapp.mapper.ClientMapper;
import gr.aueb.cf.hotelapp.model.Client;
import gr.aueb.cf.hotelapp.service.IClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/hotel/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientMapper clientMapper;
    private final IClientService clientService;

    @GetMapping("/insert")
    public String getClientForm(Model model){
        model.addAttribute("clientInsertDTO",
                new ClientInsertDTO(null, null, null, null, null, null));
        return  "client/client-form";
    }

    @PostMapping("/insert")
    public String insertClient(@Valid @ModelAttribute("clientInsertDTO") ClientInsertDTO clientInsertDTO,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "client/client-form";
        }

        try {
            ClientReadOnlyDTO savedClient = clientService.insertClient(clientInsertDTO);
            redirectAttributes.addFlashAttribute("client", savedClient);
            redirectAttributes.addFlashAttribute("successMessage", "Η εγγραφή ολοκληρώθηκε επιτυχώς!");
            return "redirect:/hotel/clients/registered";
        } catch (UsernameAlreadyExistsException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "client/client-form";
        }
    }

    @GetMapping("/registered")
    public String showClientRegistered(Model model) {
        System.out.println("Success Message: " + model.getAttribute("successMessage"));
        model.addAttribute("returnUrl", "/login");
        return "pages/registration-success";
    }

    @GetMapping("/update/{id}")
    public String getUpdateClientForm(@PathVariable Long id, Model model) throws ClientNotFoundException {
        ClientReadOnlyDTO readOnlyDTO = clientService.getClientById(id);

        ClientUpdateDTO updateDTO = new ClientUpdateDTO(
                readOnlyDTO.id(),
                readOnlyDTO.firstname(),
                readOnlyDTO.lastname(),
                readOnlyDTO.phone(),
                readOnlyDTO.vat()
        );

        model.addAttribute("clientUpdateDTO", updateDTO);
        return "client/client-update";
    }

    @PostMapping("/update")
    public String updateClient(@Valid @ModelAttribute("clientUpdateDTO") ClientUpdateDTO  clientUpdateDTO,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "client/client-update";
        }

        try{
            ClientReadOnlyDTO updateClient =  clientService.updateClient(clientUpdateDTO);
            redirectAttributes.addFlashAttribute("client", updateClient);
            return "redirect:/hotel/clients";
        }catch (UsernameAlreadyExistsException | ClientNotFoundException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "client/client-update";
        }
    }

    @GetMapping("")
    public String getAllClients(Model model) {
        List<ClientReadOnlyDTO> clients = clientService.getAllClients();
        model.addAttribute("clients", clients);
        return "client/client-list";
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            clientService.deleteClient(id);
            redirectAttributes.addFlashAttribute("successMessage", "Ο πελάτης διαγράφηκε με επιτυχία.");
        } catch (ClientNotFoundException | ClientHasReservationsException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/hotel/clients";
    }

    @GetMapping("/{id}")
    public String getClientById(@PathVariable Long id, Model model)
            throws ClientNotFoundException {

        ClientReadOnlyDTO client = clientService.getClientById(id);
        model.addAttribute("client", client);
        return "client/client-details";
    }

    @GetMapping("/search")
    public String getClientByPhone(@RequestParam("phone") String phone, Model model) {
        try {
            ClientReadOnlyDTO client = clientService.getClientByPhone(phone);
            model.addAttribute("clients", List.of(client));
            return "client/client-list";
        } catch (ClientNotFoundException e) {
            model.addAttribute("errorMessage", "Δεν βρέθηκε πελάτης με αυτό το τηλέφωνο.");
            model.addAttribute("clients", clientService.getAllClients());
            return "client/client-list";
        }
    }

    @GetMapping("/profile")
    public String getProfile(Model model, Principal principal) throws ClientNotFoundException {
        Client client = clientService.findByUsername(principal.getName());
        ClientReadOnlyDTO clientDTO = clientMapper.mapToClientReadOnlyDTO(client);
        model.addAttribute("client", clientDTO);
        return "client/profile";
    }

}
