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

    @Test
    public void insertClient_throwsExceptionIfUsernameExists() {
        ClientInsertDTO duplicateDTO = new ClientInsertDTO(
                insertedClient.username(), // Ίδιο username (email)
                "Password1!",
                "Giorgos",
                "Ioannou",
                "6988888888",
                "111222333"
        );

        Exception exception = assertThrows(Exception.class, () -> {
            clientService.insertClient(duplicateDTO);
        });

        assertTrue(exception.getMessage().toLowerCase().contains("υπάρχει ήδη"));
    }

    @Test
    public void insertClient_throwsExceptionIfVatExists() {
        ClientInsertDTO duplicateDTO = new ClientInsertDTO(
                "newuser_" + UUID.randomUUID() + "@example.com", // νέο email
                "Password1!",
                "Giorgos",
                "Ioannou",
                "6988888888",
                insertedClient.vat() // Ίδιο VAT
        );

        Exception exception = assertThrows(Exception.class, () -> {
            clientService.insertClient(duplicateDTO);
        });

        assertTrue(exception.getMessage().toLowerCase().contains("υπάρχει ήδη"));
    }

    @Test
    public void updateClient_throwsExceptionIfVatAlreadyExistsForAnotherClient() throws Exception {
        // Δημιουργούμε 2ο client
        ClientInsertDTO secondDto = new ClientInsertDTO(
                "second_" + UUID.randomUUID() + "@example.com",
                "Password1!",
                "Nikos",
                "Koutras",
                "6977777777",
                "123123123"
        );
        ClientReadOnlyDTO secondClient = clientService.insertClient(secondDto);

        // Προσπαθούμε να του κάνουμε update το VAT ώστε να είναι ίδιο με το 1ου
        ClientUpdateDTO updateDTO = new ClientUpdateDTO(
                secondClient.id(),
                secondClient.firstname(),
                secondClient.lastname(),
                secondClient.phone(),
                insertedClient.vat() // Ίδιο VAT με 1ο client
        );

        Exception exception = assertThrows(Exception.class, () -> {
            clientService.updateClient(updateDTO);
        });

        assertTrue(exception.getMessage().toLowerCase().contains("υπάρχει ήδη"));

        // καθαρισμός
        clientService.deleteClient(secondClient.id());
        userRepository.findByUsername(secondClient.username()).ifPresent(userRepository::delete);
    }

    @Test
    public void deleteClient_throwsExceptionIfNotFound() {
        Exception exception = assertThrows(Exception.class, () -> {
            clientService.deleteClient(999999L);
        });

        assertTrue(exception.getMessage().toLowerCase().contains("δεν βρέθηκε"));
    }


}
