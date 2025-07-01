package gr.aueb.cf.hotelapp.model;

import gr.aueb.cf.hotelapp.core.enums.RoomType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Η οντότητα Room αναπαριστά ένα δωμάτιο του ξενοδοχείου.
 * Περιλαμβάνει αριθμό δωματίου, τύπο, τιμή και διαθεσιμότητα.
 * Συνδέεται με πολλαπλές κρατήσεις.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "rooms")
public class Room extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_number", unique = true, nullable = false)
    private String roomNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", nullable = false)
    private RoomType roomType;

    private Double price;

    @OneToMany(mappedBy = "room")
    private List<Reservation> reservations;
}
