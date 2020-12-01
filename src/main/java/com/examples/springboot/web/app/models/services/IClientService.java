package com.examples.springboot.web.app.models.services;

import java.util.List;

import com.examples.springboot.web.app.models.entity.Client;

public interface IClientService {

	public List<Client> findAll();
	
	public Client findById(Long id);
	
	public Client save(Client client);
	
	public void delete(Long id);

}
