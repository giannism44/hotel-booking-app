package gr.aueb.cf.hotelapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "employee_status")
public class EmployeeStatus {

    @Id
    private Long userId;

    @Column(name = "total_bookings")
    private int totalBookings;

    @Column(name = "bonus_awarded")
    private boolean bonusAwarded;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @OneToOne
    @MapsId
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
