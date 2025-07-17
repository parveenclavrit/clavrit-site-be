package com.clavrit.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.clavrit.Dto.ClientDTO;
import com.clavrit.Dto.ProjectDto;
import com.clavrit.Enums.ApiStatus;
import com.clavrit.Service.ClientService;
import com.clavrit.response.ApiResponse;
import com.clavrit.response.ApisResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

	 @Autowired
	 private ClientService clientService;
	 
	 @Autowired
	 private ObjectMapper objectMapper;

	 @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	 public ApisResponse createClient(
	         @RequestPart("client") String clientJson,
	         @RequestPart(value = "logo", required = false) MultipartFile image) {

	     try {
	         ClientDTO dto = objectMapper.readValue(clientJson, ClientDTO.class);

	         ClientDTO saved = clientService.createClient(dto,image);

	         return new ApisResponse(ApiStatus.CREATED, "Client created successfully", saved);

	     } catch (Exception e) {
	         e.printStackTrace(); 
	         return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Error while creating client", e.getMessage());
	     }
	 }

	 @GetMapping
	 public ApisResponse getAllClients() {
	     try {
	         List<ClientDTO> clients = clientService.getAllClients();
	         return new ApisResponse(ApiStatus.OK, "Clients retrieved successfully", clients);
	     } catch (Exception e) {
	         return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Error retrieving clients", e.getMessage());
	     }
	 }

	 @GetMapping("/{id}")
	 public ApisResponse getClientById(@PathVariable Long id) {
	     try {
	         ClientDTO client = clientService.getClientById(id);
	         return new ApisResponse(ApiStatus.OK, "Client retrieved successfully", client);
	     } catch (Exception e) {
	         return new ApisResponse(ApiStatus.NOT_FOUND, "Client not found", e.getMessage());
	     }
	 }

	 @PutMapping(value ="/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	 public ApisResponse updateClient(@PathVariable Long id, @RequestPart("client") String clientJson,
	         @RequestPart(value = "logo", required = false) MultipartFile image) {
	     try {
	    	 ClientDTO dto = objectMapper.readValue(clientJson, ClientDTO.class);
	         ClientDTO updatedClient = clientService.updateClient(id, dto,image);
	         return new ApisResponse(ApiStatus.OK, "Client updated successfully", updatedClient);
	     } catch (Exception e) {
	         return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Error updating client", e.getMessage());
	     }
	 }

	 @DeleteMapping("/{id}")
	 public ApisResponse deleteClient(@PathVariable Long id) {
	     try {
	         clientService.deleteClient(id);
	         return new ApisResponse(ApiStatus.OK, "Client deleted successfully", null);
	     } catch (Exception e) {
	         return new ApisResponse(ApiStatus.NOT_FOUND, "Error deleting client", e.getMessage());
	     }
	 }

}

