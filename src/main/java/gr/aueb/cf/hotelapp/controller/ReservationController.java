package gr.aueb.cf.hotelapp.controller;

import gr.aueb.cf.hotelapp.core.exceptions.ClientNotFoundException;
import gr.aueb.cf.hotelapp.core.exceptions.ReservationNotFoundException;
import gr.aueb.cf.hotelapp.core.exceptions.RoomNotAvailableException;
import gr.aueb.cf.hotelapp.core.exceptions.UserNotFoundException;
import gr.aueb.cf.hotelapp.dto.ReservationInsertDTO;
import gr.aueb.cf.hotelapp.dto.ReservationReadOnlyDTO;
import gr.aueb.cf.hotelapp.dto.RoomReadOnlyDTO;
import gr.aueb.cf.hotelapp.model.Client;
import gr.aueb.cf.hotelapp.service.IClientService;
import gr.aueb.cf.hotelapp.service.IReservationService;
import gr.aueb.cf.hotelapp.service.IRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/hotel/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final IReservationService reservationService;
    private final IRoomService roomService;
    private final IClientService clientService;

   @GetMapping("/insert")
    public String getReservationForm(Model model, Principal principal) throws ClientNotFoundException {
        String username = principal.getName();
        Client client = clientService.findByUsername(username);

        ReservationInsertDTO dto = new ReservationInsertDTO(null, null, null, client.getId());

        model.addAttribute("reservationInsertDTO", dto);
        return "reservation-form";
    }

    @PostMapping("/insert")
    public String insertReservation(@Valid @ModelAttribute("reservationInsertDTO") ReservationInsertDTO reservationInsertDTO,
                                    BindingResult bindingResult,
                                    Model model,
                                    RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "reservation-form";
        }

        try {
            ReservationReadOnlyDTO savedReservation = reservationService.insertReservation(reservationInsertDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Η κράτηση καταχωρήθηκε με επιτυχία!");
            return "redirect:/hotel/reservations/success";
        } catch (UserNotFoundException | RoomNotAvailableException | ClientNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "reservation-form";
        }
    }


    @GetMapping("/delete/{id}")
    public String deleteReservation(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            reservationService.deleteReservation(id);
            redirectAttributes.addFlashAttribute("successMessage", "Η κράτηση ακυρώθηκε.");
        } catch (ReservationNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/hotel/reservations";
    }

    @GetMapping("")
    public String getAllReservations(Model model) {
        model.addAttribute("reservations", reservationService.getAllReservations());
        return "reservation-list";
    }

    @GetMapping("/{id}")
    public String getReservationById(@PathVariable Long id, Model model) throws ReservationNotFoundException {
        ReservationReadOnlyDTO reservation = reservationService.getReservationById(id);
        model.addAttribute("reservation", reservation);
        return "reservation-details";
    }

    @GetMapping("/success")
    public String showReservationSuccess(Model model) {
        if (!model.containsAttribute("successMessage")) {
            model.addAttribute("successMessage", "Η κράτηση ολοκληρώθηκε επιτυχώς!");
        }
        model.addAttribute("returnUrl", "/");
        return "pages/registration-success";
    }

    @GetMapping("/availability")
    public String showAvailabilityForm(Model model) {
        model.addAttribute("checkIn", null);
        model.addAttribute("checkOut", null);
        return "reservation-availability";
    }

    @GetMapping("/available-rooms")
    @ResponseBody
    public List<RoomReadOnlyDTO> getAvailableRoomsForDatesAjax(
            @RequestParam("checkIn") String checkInStr,
            @RequestParam("checkOut") String checkOutStr) {

        LocalDate checkIn = LocalDate.parse(checkInStr);
        LocalDate checkOut = LocalDate.parse(checkOutStr);

        return roomService.getAvailableRoomsForDates(checkIn, checkOut);
    }
}
