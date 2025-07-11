package com.clavrit.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.clavrit.Dto.ClientDTO;

public interface ClientService {
    ClientDTO createClient(ClientDTO clientDTO,MultipartFile logoFile);
    List<ClientDTO> getAllClients();
    ClientDTO getClientById(Long id);
    ClientDTO updateClient(Long id, ClientDTO clientDTO,MultipartFile logoFile);
    void deleteClient(Long id);
}

