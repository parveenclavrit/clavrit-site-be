package com.clavrit.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clavrit.Dto.ClientDTO;
import com.clavrit.Entity.Client;
import com.clavrit.Repository.ClientRepository;
import com.clavrit.Service.ClientService;
import com.clavrit.mapper.ClientMapper;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public ClientDTO createClient(ClientDTO clientDTO) {
        Client client = ClientMapper.toEntity(clientDTO);
        return ClientMapper.toDTO(clientRepository.save(client));
    }

    @Override
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
            .map(ClientMapper::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public ClientDTO getClientById(Long id) {
        Client client = clientRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Client not found"));
        return ClientMapper.toDTO(client);
    }

    @Override
    public ClientDTO updateClient(Long id, ClientDTO clientDTO) {
        Client existing = clientRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Client not found"));
        existing.setName(clientDTO.getName());
        existing.setCompany(clientDTO.getCompany());
        existing.setEmail(clientDTO.getEmail());
        existing.setPhone(clientDTO.getPhone());
        return ClientMapper.toDTO(clientRepository.save(existing));
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}

