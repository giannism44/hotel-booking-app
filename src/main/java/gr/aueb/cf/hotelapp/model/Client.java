package gr.aueb.cf.hotelapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Η οντότητα Client αναπαριστά έναν πελάτη του ξενοδοχείου.
 * Κάθε πελάτης έχει στοιχεία επικοινωνίας, έναν λογαριασμό σύνδεσης (User),
 * μια κατάσταση επιβράβευσης (ClientStatus) και λίστα από κρατήσεις.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "clients")
public class Client extends AbstractEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(length = 20)
    private String phone;

    @Column(unique = true)
    private String vat;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "client",  cascade = CascadeType.ALL)
    private ClientStatus clientStatus;

    @OneToMany(mappedBy = "client")
    private List<Reservation> reservations;
}
