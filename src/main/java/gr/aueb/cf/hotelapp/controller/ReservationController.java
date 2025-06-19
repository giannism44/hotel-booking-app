package gr.aueb.cf.hotelapp.controller;

import gr.aueb.cf.hotelapp.core.exceptions.ClientNotFoundException;
import gr.aueb.cf.hotelapp.core.exceptions.ReservationNotFoundException;
import gr.aueb.cf.hotelapp.core.exceptions.RoomNotAvailableException;
import gr.aueb.cf.hotelapp.core.exceptions.UserNotFoundException;
import gr.aueb.cf.hotelapp.dto.ReservationInsertDTO;
import gr.aueb.cf.hotelapp.dto.ReservationReadOnlyDTO;
import gr.aueb.cf.hotelapp.service.IReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/hotel/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final IReservationService reservationService;

    @GetMapping("/insert")
    public String getReservationForm(Model model) {
        model.addAttribute("reservationInsertDTO",
                new ReservationInsertDTO(null, null, null, null));
        return "reservation-form";
    }

    @PostMapping("/insert")
    public String insertReservation(@Valid @ModelAttribute("reservationInsertDTO")ReservationInsertDTO reservationInsertDTO,
                                    BindingResult bindingResult,
                                    Model model,
                                    RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()) {
            return "reservation-form";
        }

        try {
            ReservationReadOnlyDTO savedReservation = reservationService.insertReservation(reservationInsertDTO);
            redirectAttributes.addFlashAttribute("reservation", savedReservation);
            return "redirect:/hotel/reservations";
        }catch (UserNotFoundException | RoomNotAvailableException | ClientNotFoundException e){
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
}
