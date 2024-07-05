package com.alcode.ecommercespb.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alcode.ecommercespb.Entity.Client;
import com.alcode.ecommercespb.dto.ClientDto;
import com.alcode.ecommercespb.exception.RessourceNotFoundException;
import com.alcode.ecommercespb.repository.Clientrepository;

@Service
public class ClientService {
	
	@Autowired
	private Clientrepository clientrepository;
	
	private final ModelMapper mapper = new ModelMapper();

	public List<Client> getAllCLient(){
		
		return clientrepository.findAll();
	}
	
	public Client getClientById(Long id) {
		Client client  = clientrepository.findById(id).orElseThrow(RessourceNotFoundException::new);
		return client;
	}
	
	public Client getClientByEmail(String email) {
		Client cli = clientrepository.findByEmail(email);
		return cli;
	}
	
	public void deleteClientById(Long id) throws Exception {
		if(!clientrepository.existsById(id)) {
			throw new Exception("Client qui a l'id "+id+" n'existe pas");
		}
		clientrepository.deleteById(id);
	}
	
	public Client updateClient(Long id,ClientDto clientDto) {
		Client clientbd = getClientById(id);
		
		mapper.map(clientDto,clientbd);
		return clientrepository.save(clientbd);
	}
	
	public Client postClient(ClientDto clientDto) {
		
		Client client = mapper.map(clientDto, Client.class);
		
		
		return clientrepository.save(client);
	}
	
}
