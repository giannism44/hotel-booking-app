package gr.aueb.cf.hotelapp.init;

import gr.aueb.cf.hotelapp.core.enums.RoomType;
import gr.aueb.cf.hotelapp.model.Room;
import gr.aueb.cf.hotelapp.repository.RoomRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * RoomDataLoader δημιουργεί αυτόματα 50 δωμάτια κατά την εκκίνηση της εφαρμογής,
 * αν η βάση δεν περιέχει ήδη δεδομένα.
 * Τα δωμάτια κατανέμονται σε 5 ορόφους με διαφορετικό τύπο και τιμή:
 * - Όροφοι 1-2: SINGLE (75€)
 * - Όροφοι 3-4: DOUBLE (100€)
 * - Όροφος 5: SUITE (150€)
 */
@Component
@RequiredArgsConstructor
public class RoomDataLoader {

    private final RoomRepository roomRepository;

    @PostConstruct
    public void initRooms() {
        long count =  roomRepository.count();

        if(count > 0) return;

        for (int floor = 1; floor <= 5; floor++) {
            for (int roomNum = 1; roomNum <= 10; roomNum++) {
                String roomNumber = floor + String.format("%02d", roomNum);

//                if (roomRepository.findByRoomNumber(roomNumber).isEmpty()) {
                    Room room = new Room();
                    room.setRoomNumber(roomNumber);

                    if (floor == 5) {
                        room.setRoomType(RoomType.SUITE);
                        room.setPrice(150.0);
                    } else if (floor >= 3) {
                        room.setRoomType(RoomType.DOUBLE);
                        room.setPrice(100.0);
                    } else {
                        room.setRoomType(RoomType.SINGLE);
                        room.setPrice(75.0);
                    }


                    roomRepository.save(room);
//              }
            }
        }
    }
}
