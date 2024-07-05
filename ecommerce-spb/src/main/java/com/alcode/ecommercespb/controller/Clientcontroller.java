package com.alcode.ecommercespb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alcode.ecommercespb.Entity.Client;
import com.alcode.ecommercespb.dto.ClientDto;
import com.alcode.ecommercespb.service.ClientService;

import lombok.AllArgsConstructor;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/client")
public class Clientcontroller {
	
	@Autowired
	private ClientService clientService;
	
	@GetMapping
	public List<Client> getAllClients() {
		return clientService.getAllCLient();
	}
	
	@PostMapping
	public Client createClient(@RequestBody ClientDto clientDto) {
		return clientService.postClient(clientDto);
	}
	
	@GetMapping("/{id}")
	public Client getClientById(@PathVariable Long id) {
		
		return clientService.getClientById(id);
	}
	
	@GetMapping("/email/{email}")
	public Client getClientByEmail(@PathVariable String email) {
		
		return clientService.getClientByEmail(email);
	}
	
	@PutMapping("/{id}")
	public Client updateClient(@PathVariable Long id,@RequestBody ClientDto clientDto) {
		
		return clientService.updateClient(id, clientDto);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void deleteClient(@PathVariable Long id) throws Exception {
		 clientService.deleteClientById(id);
	}
	
}
