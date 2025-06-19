package gr.aueb.cf.hotelapp.model;

import jakarta.persistence.*;
import lombok.*;


/**
 * Η οντότητα ClientStatus καταγράφει πόσες κρατήσεις έχει κάνει κάθε πελάτης.
 * Χρησιμοποιείται για την ενεργοποίηση έκπτωσης βάσει των συνολικών κρατήσεων.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "client_status")
public class ClientStatus {

    @Id
    private Long clientId;

    @Column(name = "total_bookings")
    private int totalBookings;

    @Column(name = "discount_active")
    private boolean discountActive;

    @OneToOne
    @MapsId
    @JoinColumn(name = "client_id")
    private Client client;
}
