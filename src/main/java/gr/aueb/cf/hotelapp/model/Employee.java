package gr.aueb.cf.hotelapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "employees")
public class Employee extends AbstractEntity{

    @Id
    private Long id;

    private String firstname;
    private String lastname;
}
