package com.examples.springboot.web.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.examples.springboot.web.app.models.entity.Client;

public interface IClientDao extends CrudRepository<Client, Long>{
	
}
