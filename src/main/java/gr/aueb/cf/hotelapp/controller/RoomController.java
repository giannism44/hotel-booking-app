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

import java.util.List;

@Controller
@RequestMapping("/hotel/rooms")
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
                readOnlyDTO.price()
        );

        model.addAttribute("roomUpdateDTO", updateDTO);
        return "room-update";
    }

    @PostMapping("/update")
    public String updateRoom(@Valid @ModelAttribute("roomUpdateDTO") RoomUpdateDTO roomUpdateDTO,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()) {
            return "room-update";
        }

        try {
            RoomReadOnlyDTO updateRoom = roomService.updateRoom(roomUpdateDTO);
            redirectAttributes.addFlashAttribute("room", updateRoom);
            return "redirect:/hotel/rooms/management/rooms";
        }catch (RoomNotFoundException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "room-update";
        }
    }

    @GetMapping("")
    public String getAllRooms(Model model){
        List<RoomReadOnlyDTO> rooms = roomService.getAllRooms();
        model.addAttribute("rooms", rooms);
        return "room-list";
    }

    @GetMapping("/management/rooms")
    public String getRoomsForManagement(Model model) {
        List<RoomReadOnlyDTO> rooms = roomService.getAllRooms();
        model.addAttribute("rooms", rooms);
        return "room-management-list";
    }

    @GetMapping("/{id}")
    public String getRoomById(@PathVariable Long id, Model model)
            throws RoomNotFoundException {

        RoomReadOnlyDTO room = roomService.getRoomById(id);
        model.addAttribute("room", room);
        return "room-details";
    }

}
