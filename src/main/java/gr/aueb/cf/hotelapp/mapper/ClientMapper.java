package gr.aueb.cf.hotelapp.mapper;

import gr.aueb.cf.hotelapp.core.enums.Role;
import gr.aueb.cf.hotelapp.dto.ClientInsertDTO;
import gr.aueb.cf.hotelapp.dto.ClientReadOnlyDTO;
import gr.aueb.cf.hotelapp.dto.ClientUpdateDTO;
import gr.aueb.cf.hotelapp.model.Client;
import gr.aueb.cf.hotelapp.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientMapper {

    private final PasswordEncoder passwordEncoder;

    public ClientReadOnlyDTO mapToClientReadOnlyDTO(Client client) {
        return new ClientReadOnlyDTO(
                client.getId(),
                client.getFirstname(),
                client.getLastname(),
                client.getPhone(),
                client.getVat(),
                client.getUser().getUsername()
        );
    }

    public Client mapToClientEntity(ClientInsertDTO dto) {
        Client client = new Client();
        client.setFirstname(dto.firstname());
        client.setLastname(dto.lastname());
        client.setPhone(dto.phone());
        client.setVat(dto.vat());

        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(Role.CLIENT);

        client.setUser(user);

        return client;
    }

    public Client mapToClientEntity(ClientUpdateDTO dto) {
        Client client = new Client();
        client.setId(dto.id());
        client.setFirstname(dto.firstname());
        client.setLastname(dto.lastname());
        client.setPhone(dto.phone());
        client.setVat(dto.vat());

        return client;
    }

}
