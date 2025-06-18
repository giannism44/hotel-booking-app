package gr.aueb.cf.hotelapp.controller;

import gr.aueb.cf.hotelapp.core.exceptions.RoomNotFoundException;
import gr.aueb.cf.hotelapp.dto.RoomReadOnlyDTO;
import gr.aueb.cf.hotelapp.dto.RoomUpdateDTO;
import gr.aueb.cf.hotelapp.mapper.RoomMapper;
import gr.aueb.cf.hotelapp.service.IRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/hotel/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomMapper roomMapper;
    private final IRoomService roomService;

    @GetMapping("/update/{id}")
    public String getUpdateRoomForm(@PathVariable Long id, Model model)
    throws RoomNotFoundException {

        RoomReadOnlyDTO readOnlyDTO = roomService.getRoomById(id);

        RoomUpdateDTO updateDTO = new RoomUpdateDTO(
                readOnlyDTO.id(),
                readOnlyDTO.roomType(),
                readOnlyDTO.price(),
                readOnlyDTO.isAvailable()
        );

        model.addAttribute("roomUpdateDTO", updateDTO);
        return "room-form";
    }

    @PostMapping("/update")
    public String updateRoom(@Valid @ModelAttribute("roomUpdateDTO") RoomUpdateDTO roomUpdateDTO,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()) {
            return "room-form";
        }

        try {
            RoomReadOnlyDTO updateRoom = roomService.updateRoom(roomUpdateDTO);
            redirectAttributes.addFlashAttribute("room", updateRoom);
            return "redirect:/hotel/rooms";
        }catch (RoomNotFoundException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "room-form";
        }
    }


}
