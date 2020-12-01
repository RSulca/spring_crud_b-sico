package com.examples.springboot.web.app.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.examples.springboot.web.app.models.entity.Client;
import com.examples.springboot.web.app.models.services.IClientService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ClientRestController {

	@Autowired
	private IClientService clientService;

	@GetMapping("/clients")
	public List<Client> getClients() {
		return clientService.findAll();
	}

	@GetMapping("/clients/{id}")
	public ResponseEntity<Map<String, Object>> getClient(@PathVariable Long id) {
		Client client = null;
		Map<String, Object> response = new HashMap<>();
		try {
			client = clientService.findById(id);
			if (client == null) {
				response.put("ok", false);
				response.put("message", "Client not found.");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			response.put("ok", true);
			response.put("data", client);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("ok", false);
			response.put("message", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/clients")
	public ResponseEntity<Map<String, Object>> saveClient(@RequestBody Client client) {
		Client clientCreated = null;
		Map<String, Object> response = new HashMap<>();
		try {
			clientCreated = clientService.save(client);
			response.put("ok", true);
			response.put("data", clientCreated);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			response.put("ok", false);
			response.put("message", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/clients/{id}")
	public ResponseEntity<Map<String, Object>> updateClient(@PathVariable Long id, @RequestBody Client client) {
		Client clientCurrent = null;
		Map<String, Object> response = new HashMap<>();
		try {
			clientCurrent = clientService.findById(id);
			if (client == null) {
				response.put("ok", false);
				response.put("message", "Client not found.");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			clientCurrent.setName(client.getName());
			clientCurrent.setLastName(client.getLastName());
			clientCurrent.setEmail(client.getEmail());
			Client alt = clientService.save(clientCurrent);
			response.put("ok", true);
			response.put("data", alt);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("ok", false);
			response.put("message", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/clients/{id}")
	public ResponseEntity<Map<String, Object>> deleteClient(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			clientService.delete(id);
			response.put("ok", true);
			response.put("data", "Client deleted.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("ok", false);
			response.put("message", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
