package com.clavrit.Service;

import java.util.List;

import com.clavrit.Dto.ClientDTO;

public interface ClientService {
    ClientDTO createClient(ClientDTO clientDTO);
    List<ClientDTO> getAllClients();
    ClientDTO getClientById(Long id);
    ClientDTO updateClient(Long id, ClientDTO clientDTO);
    void deleteClient(Long id);
}

