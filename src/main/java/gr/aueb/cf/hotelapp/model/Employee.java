package gr.aueb.cf.hotelapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Η οντότητα Employee αναπαριστά έναν υπάλληλο του ξενοδοχείου.
 * Κάθε υπάλληλος έχει προσωπικά στοιχεία, έναν λογαριασμό σύνδεσης (User),
 * και μια καταγραφή για την κατάσταση επιβράβευσης (EmployeeStatus).
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "employees")
public class Employee extends AbstractEntity{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private EmployeeStatus employeeStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}
