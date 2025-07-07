package com.clavrit.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clavrit.Dto.ClientDTO;
import com.clavrit.Service.ClientService;
import com.clavrit.response.ApiResponse;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

	 @Autowired
	    private ClientService clientService;

	    @PostMapping
	    public ResponseEntity<ApiResponse<ClientDTO>> createClient(@Valid @RequestBody ClientDTO dto) {
	        ClientDTO saved = clientService.createClient(dto);
	        ApiResponse<ClientDTO> response = new ApiResponse<>(
	                true,
	                "Client created successfully.",
	                saved
	        );
	        return ResponseEntity.ok(response);
	    }


	    @GetMapping
	    public ResponseEntity<ApiResponse<List<ClientDTO>>> getAllClients() {
	        List<ClientDTO> clients = clientService.getAllClients();
	        ApiResponse<List<ClientDTO>> response = new ApiResponse<>(
	                true,
	                "Clients retrieved successfully.",
	                clients
	        );
	        return ResponseEntity.ok(response);
	    }


	    @GetMapping("/{id}")
	    public ResponseEntity<ApiResponse<ClientDTO>> getClientById(@PathVariable Long id) {
	        ClientDTO client = clientService.getClientById(id);
	        ApiResponse<ClientDTO> response = new ApiResponse<>(
	                true,
	                "Client retrieved successfully.",
	                client
	        );
	        return ResponseEntity.ok(response);
	    }


	    @PutMapping("/{id}")
	    public ResponseEntity<ApiResponse<ClientDTO>> updateClient(@PathVariable Long id, @RequestBody ClientDTO dto) {
	        ClientDTO updatedClient = clientService.updateClient(id, dto);
	        ApiResponse<ClientDTO> response = new ApiResponse<>(
	                true,
	                "Client updated successfully.",
	                updatedClient
	        );
	        return ResponseEntity.ok(response);
	    }


	    @DeleteMapping("/{id}")
	    public ResponseEntity<ApiResponse<Void>> deleteClient(@PathVariable Long id) {
	        clientService.deleteClient(id);
	        ApiResponse<Void> response = new ApiResponse<>(
	                true,
	                "Client deleted successfully.",
	                null
	        );
	        return ResponseEntity.ok(response);
	    }
}

