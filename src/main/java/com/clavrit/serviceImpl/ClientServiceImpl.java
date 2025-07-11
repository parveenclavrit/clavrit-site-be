package com.clavrit.serviceImpl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.clavrit.Dto.ClientDTO;
import com.clavrit.Entity.Client;
import com.clavrit.Repository.ClientRepository;
import com.clavrit.Service.ClientService;
import com.clavrit.mapper.ClientMapper;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;
    
    Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Value("${file.upload.ClientLogo}")
    private String basePath;

    @Value("${LOCAL_BASE_PATH}")
    private String LOCAL_BASE_PATH;

    @Value("${PUBLIC_URL_BASE}")
    private String PUBLIC_URL_BASE;

    @Override
    public ClientDTO createClient(ClientDTO clientDTO, MultipartFile logoFile) {
        try {
            Client client = ClientMapper.toEntity(clientDTO);

            if (logoFile != null && !logoFile.isEmpty()) {
                String logoUrl = saveUploadedFile(logoFile);
                client.setLogoImage(logoUrl);
            }

            client.setCreatedAt(LocalDateTime.now());

            Client saved = clientRepository.save(client);
            log.info("Client created successfully with ID: {}", saved.getId());
            return ClientMapper.toDTO(saved);

        } catch (Exception e) {
            log.error("Error while creating client: {}", e.getMessage());
            throw new RuntimeException("Failed to create client: " + e.getMessage());
        }
    }

    @Override
    public ClientDTO getClientById(Long id) {
        try {
            Optional<Client> optional = clientRepository.findById(id);
            if (!optional.isPresent()) {
                throw new RuntimeException("Client not found with ID: " + id);
            }

            log.info("Fetched client with ID: {}", id);
            return ClientMapper.toDTO(optional.get());

        } catch (Exception e) {
            log.error("Error while fetching client: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch client: " + e.getMessage());
        }
    }

    @Override
    public List<ClientDTO> getAllClients() {
        try {
            List<Client> clients = clientRepository.findAll();
            List<ClientDTO> dtos = new ArrayList<>();
            for (Client client : clients) {
                dtos.add(ClientMapper.toDTO(client));
            }
            log.info("Total {} clients fetched", dtos.size());
            return dtos;

        } catch (Exception e) {
            log.error("Error while fetching all clients: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch clients: " + e.getMessage());
        }
    }

    @Override
    public ClientDTO updateClient(Long id, ClientDTO dto, MultipartFile logoFile) {
        try {
            Optional<Client> optional = clientRepository.findById(id);
            if (!optional.isPresent()) {
                throw new RuntimeException("Client not found with ID: " + id);
            }

            Client existing = optional.get();

            if (dto.getName() != null) existing.setName(dto.getName());
            if (dto.getEmail() != null) existing.setEmail(dto.getEmail());
            if (dto.getPhone() != null) existing.setPhone(dto.getPhone());
            if (dto.getCompany() != null) existing.setCompany(dto.getCompany());

            if (logoFile != null && !logoFile.isEmpty()) {
                String logoUrl = saveUploadedFile(logoFile);
                existing.setLogoImage(logoUrl);
            }

            Client updated = clientRepository.save(existing);
            log.info("Client updated with ID: {}", id);
            return ClientMapper.toDTO(updated);

        } catch (Exception e) {
            log.error("Error while updating client: {}", e.getMessage());
            throw new RuntimeException("Failed to update client: " + e.getMessage());
        }
    }

    @Override
    public void deleteClient(Long id) {
        try {
            Optional<Client> optional = clientRepository.findById(id);
            if (!optional.isPresent()) {
                throw new RuntimeException("Client not found with ID: " + id);
            }

            clientRepository.delete(optional.get());
            log.info("Client deleted with ID: {}", id);

        } catch (Exception e) {
            log.error("Error while deleting client: {}", e.getMessage());
            throw new RuntimeException("Failed to delete client: " + e.getMessage());
        }
    }

    private String saveUploadedFile(MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is empty or null");
        }

        File dir = new File(basePath);
        if (!dir.exists()) dir.mkdirs();

        String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String fullPath = basePath + uniqueFileName;

        file.transferTo(new File(fullPath));

        return fullPath.replace(LOCAL_BASE_PATH, PUBLIC_URL_BASE).replace("\\", "/");
    }

}

