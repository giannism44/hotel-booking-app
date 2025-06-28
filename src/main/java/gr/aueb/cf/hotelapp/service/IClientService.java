package gr.aueb.cf.hotelapp.service;

import gr.aueb.cf.hotelapp.core.exceptions.ClientNotFoundException;
import gr.aueb.cf.hotelapp.core.exceptions.UsernameAlreadyExistsException;
import gr.aueb.cf.hotelapp.dto.ClientInsertDTO;
import gr.aueb.cf.hotelapp.dto.ClientReadOnlyDTO;
import gr.aueb.cf.hotelapp.dto.ClientUpdateDTO;
import gr.aueb.cf.hotelapp.model.Client;

import java.util.List;

public interface IClientService {
    ClientReadOnlyDTO insertClient(ClientInsertDTO dto)
            throws UsernameAlreadyExistsException;
    ClientReadOnlyDTO updateClient(ClientUpdateDTO dto)
            throws ClientNotFoundException, UsernameAlreadyExistsException;
    void deleteClient(Long id)
            throws ClientNotFoundException ;
    List<ClientReadOnlyDTO> getAllClients();
    ClientReadOnlyDTO getClientById(Long id)
            throws ClientNotFoundException;
    Client findByUsername(String username) throws ClientNotFoundException;
}
