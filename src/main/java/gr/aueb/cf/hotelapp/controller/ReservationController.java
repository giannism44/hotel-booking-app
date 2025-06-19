package gr.aueb.cf.hotelapp.controller;

import gr.aueb.cf.hotelapp.core.exceptions.ClientNotFoundException;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
