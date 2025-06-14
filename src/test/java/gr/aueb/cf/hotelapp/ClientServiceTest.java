package gr.aueb.cf.hotelapp;

import gr.aueb.cf.hotelapp.dto.ClientInsertDTO;
import gr.aueb.cf.hotelapp.dto.ClientReadOnlyDTO;
import gr.aueb.cf.hotelapp.dto.ClientUpdateDTO;
import gr.aueb.cf.hotelapp.repository.ClientRepository;
import gr.aueb.cf.hotelapp.repository.UserRepository;
import gr.aueb.cf.hotelapp.service.IClientService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = HotelBookingSystemApplication.class)
public class ClientServiceTest {

    @Autowired
    private IClientService clientService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    private ClientReadOnlyDTO insertedClient;

    @BeforeEach
    public void setup() throws Exception {
        String randomEmail = "testclient_" + UUID.randomUUID() + "@example.com";
        String randomVat = String.valueOf((int)(Math.random() * 1_000_000_000));

        ClientInsertDTO dto = new ClientInsertDTO(
                randomEmail,
                "Password1!",
                "Maria",
                "Papadaki",
                "6999999999",
                randomVat
        );

        insertedClient = clientService.insertClient(dto);
    }

    @AfterEach
    public void cleanup() {
        if (insertedClient != null) {
            clientRepository.deleteById(insertedClient.id());
            userRepository.findByUsername(insertedClient.username())
                    .ifPresent(user -> userRepository.delete(user));
        }
    }

    @Test
    public void insertClient_successful() {
        assertNotNull(insertedClient);
        assertEquals("Maria", insertedClient.firstname());
        assertEquals("Papadaki", insertedClient.lastname());
        assertTrue(insertedClient.username().startsWith("testclient_"));
    }

    @Test
    public void updateClient_successful() throws Exception {
        ClientUpdateDTO updateDTO = new ClientUpdateDTO(
                insertedClient.id(),
                "Katerina",
                "Dimitriou",
                "6988888888",
                "987654321"
        );

        ClientReadOnlyDTO updated = clientService.updateClient(updateDTO);

        assertNotNull(updated);
        assertEquals("Katerina", updated.firstname());
        assertEquals("Dimitriou", updated.lastname());
        assertEquals(insertedClient.username(), updated.username());
    }

    @Test
    public void deleteClient_successful() throws Exception {
        clientService.deleteClient(insertedClient.id());

        boolean exists = clientRepository.findById(insertedClient.id()).isPresent();
        assertFalse(exists);

        userRepository.findByUsername(insertedClient.username())
                .ifPresent(user -> userRepository.delete(user));
    }

    @Test
    public void getClientById_successful() throws Exception {
        ClientReadOnlyDTO found = clientService.getClientById(insertedClient.id());

        assertNotNull(found);
        assertEquals("Maria", found.firstname());
        assertEquals("Papadaki", found.lastname());
    }

    @Test
    public void getClientById_throwsExceptionIfNotFound() {
        Exception exception = assertThrows(Exception.class, () -> {
            clientService.getClientById(99999L);
        });

        assertTrue(exception.getMessage().contains("δεν βρέθηκε"));
    }

    @Test
    public void getAllClients_returnsList() throws Exception {
        List<ClientReadOnlyDTO> clients = clientService.getAllClients();

        assertNotNull(clients);
        assertTrue(clients.size() >= 1);
    }
}
