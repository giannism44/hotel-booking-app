package gr.aueb.cf.hotelapp.model;

import gr.aueb.cf.hotelapp.core.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


/**
 * Η οντότητα Reservation αναπαριστά μια κράτηση δωματίου.
 * Περιλαμβάνει ημερομηνίες check-in/check-out, κατάσταση κράτησης,
 * καθώς και συνδέσεις με πελάτη, δωμάτιο και χρήστη που την καταχώρησε.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "reservations")
public class Reservation extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "check_in", nullable = false)
    private LocalDate checkIn;

    @Column(name = "check_out", nullable = false)
    private LocalDate checkOut;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}