package gr.aueb.cf.hotelapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "clients")
public class Client extends AbstractEntity {

    @Id
    private Long id;

    private String firstname;
    private String lastname;

    private String phone;

    @Column(unique = true)
    private String vat;
}
