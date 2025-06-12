package gr.aueb.cf.hotelapp.service;

import gr.aueb.cf.hotelapp.core.exceptions.RoomNotFoundException;
import gr.aueb.cf.hotelapp.core.exceptions.RoomNumberAlreadyExistsException;
import gr.aueb.cf.hotelapp.dto.RoomInsertDTO;
import gr.aueb.cf.hotelapp.dto.RoomReadOnlyDTO;
import gr.aueb.cf.hotelapp.dto.RoomUpdateDTO;
import gr.aueb.cf.hotelapp.mapper.RoomMapper;
import gr.aueb.cf.hotelapp.model.Room;
import gr.aueb.cf.hotelapp.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements IRoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public RoomReadOnlyDTO insertRoom(RoomInsertDTO dto)
            throws RoomNumberAlreadyExistsException {

        if (roomRepository.findByRoomNumber(dto.roomNumber()).isPresent()) {
            throw new RoomNumberAlreadyExistsException("Room", "Το δωμάτιο με αριθμό " + dto.roomNumber() + " υπάρχει ήδη");
        }

        Room room = roomMapper.mapToRoomEntity(dto);
        Room savedRoom = roomRepository.save(room);

        return roomMapper.mapToRoomReadOnlyDTO(savedRoom);
    }

    @Override
    public RoomReadOnlyDTO updateRoom(RoomUpdateDTO dto)
            throws RoomNotFoundException {
        Room existingRoom = roomRepository.findById(dto.id())
                .orElseThrow(() -> new RoomNotFoundException("Room", "Το δωμάτιο με id " + dto.id() + " δεν βρέθηκε."));

        existingRoom.setRoomType(dto.roomType());
        existingRoom.setPrice(dto.price());
        existingRoom.setIsAvailable(dto.isAvailable());

        Room updatedRoom = roomRepository.save(existingRoom);
        return roomMapper.mapToRoomReadOnlyDTO(updatedRoom);
    }

    @Override
    public void deleteRoom(Long id)
            throws RoomNotFoundException {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room", "Το δωμάτιο με id " + id + " δεν βρέθηκε."));

        roomRepository.delete(room);
    }

    @Override
    public List<RoomReadOnlyDTO> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(roomMapper::mapToRoomReadOnlyDTO)
                .toList();
    }

    @Override
    public RoomReadOnlyDTO getRoomById(Long id) throws RoomNotFoundException {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room", "Το δωμάτιο με id " + id + " δεν βρέθηκε."));

        return roomMapper.mapToRoomReadOnlyDTO(room);
    }
}

