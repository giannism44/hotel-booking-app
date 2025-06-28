package gr.aueb.cf.hotelapp.service;

import gr.aueb.cf.hotelapp.core.exceptions.ClientNotFoundException;
import gr.aueb.cf.hotelapp.core.exceptions.UsernameAlreadyExistsException;
import gr.aueb.cf.hotelapp.dto.ClientInsertDTO;
import gr.aueb.cf.hotelapp.dto.ClientReadOnlyDTO;
import gr.aueb.cf.hotelapp.dto.ClientUpdateDTO;
import gr.aueb.cf.hotelapp.mapper.ClientMapper;
import gr.aueb.cf.hotelapp.model.Client;
import gr.aueb.cf.hotelapp.repository.ClientRepository;
import gr.aueb.cf.hotelapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService{

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ClientReadOnlyDTO insertClient(ClientInsertDTO dto)
            throws UsernameAlreadyExistsException {

        if (clientRepository.findByVat(dto.vat()).isPresent()) {
            throw new UsernameAlreadyExistsException("Client", "Ο πελάτης με vat " + dto.vat() + " υπάρχει ήδη");
        }

        if (userRepository.findByUsername(dto.username()).isPresent()) {
            throw new UsernameAlreadyExistsException ("User" , "Ο χρήστης με username: " + dto.username() + "υπάρχει ήδη");
        }

        Client client = clientMapper.mapToClientEntity(dto);
        Client savedClient = clientRepository.save(client);

        return clientMapper.mapToClientReadOnlyDTO(savedClient);
    }


    @Override
    @Transactional(rollbackOn = Exception.class)
    public ClientReadOnlyDTO updateClient(ClientUpdateDTO dto)
            throws ClientNotFoundException, UsernameAlreadyExistsException{

        Client existingClient = clientRepository.findById(dto.id())
                .orElseThrow(() -> new ClientNotFoundException("Client", "Ο πελάτης με id " + dto.id() + " δεν βρέθηκε."));

        Optional<Client> clientWithSameVat = clientRepository.findByVat(dto.vat());

        if (clientWithSameVat.isPresent() && !clientWithSameVat.get().getId().equals(dto.id())) {
            throw new UsernameAlreadyExistsException("Client", "Ο πελάτης με vat " + dto.vat() + " υπάρχει ήδη");
        }

        existingClient.setFirstname(dto.firstname());
        existingClient.setLastname(dto.lastname());
        existingClient.setPhone(dto.phone());
        existingClient.setVat(dto.vat());

        Client updatedClient = clientRepository.save(existingClient);

        return clientMapper.mapToClientReadOnlyDTO(updatedClient);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void deleteClient(Long id)
            throws ClientNotFoundException {

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client", "Ο πελάτης με id " + id + " δεν βρέθηκε."));

        clientRepository.delete(client);
    }

    @Override
    public List<ClientReadOnlyDTO> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(clientMapper::mapToClientReadOnlyDTO)
                .toList();
    }

    @Override
    public ClientReadOnlyDTO getClientById(Long id)
            throws ClientNotFoundException{
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client", "Ο πελάτης με id " + id + " δεν βρέθηκε."));

        return clientMapper.mapToClientReadOnlyDTO(client);
    }

    @Override
    public Client findByUsername(String username)
            throws ClientNotFoundException {
        return clientRepository.findByUserUsername(username)
                .orElseThrow(() -> new ClientNotFoundException("Client", "Ο πελάτης με username " + username + " δεν βρέθηκε."));
    }
}
