package com.estudos.vendas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import com.estudos.vendas.entity.Cliente;
import com.estudos.vendas.exceptions.NaoEncontradoException;
import com.estudos.vendas.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	public List<Cliente> find(Cliente filtro){
		ExampleMatcher matcher = ExampleMatcher
									.matching()
									.withIgnoreCase()
									.withStringMatcher(StringMatcher.CONTAINING);
		
		Example<Cliente> example = Example.of(filtro, matcher);
		return repository.findAll(example);
	}
	
	public Cliente findById(Long id) {
		return repository
				.findById(id)
				.orElseThrow( () -> new NaoEncontradoException("Cliente não encotrado"));
	}
	
	public Cliente save(Cliente cliente) {
		return repository.save(cliente);
	}
	
	public void delete(Long id) {
		repository
			.findById(id)
			.map(cliente -> {
				repository.delete(cliente);
				return cliente;
			}).orElseThrow( () -> new NaoEncontradoException("Cliente não encotrado"));
	}
	
	public Cliente update(Long id, Cliente cliente) {
		return repository
					.findById(id)
					.map( clienteExistente -> {
						cliente.setId(clienteExistente.getId());
						repository.save(cliente);
						return cliente;
					}).orElseThrow( () -> new NaoEncontradoException( "Cliente não encotrado"));
	}
	
}
