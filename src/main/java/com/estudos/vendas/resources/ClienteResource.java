package com.estudos.vendas.resources;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.estudos.vendas.entity.Cliente;
import com.estudos.vendas.services.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;
	
	@GetMapping
	public List<Cliente> find(Cliente filtro){
		return service.find(filtro);
	}
	
	@GetMapping("/{id}")
	private Cliente findById(@PathVariable Long id) {
		return service.findById(id);
	}
	
	@PostMapping
	@ResponseStatus(CREATED)
	public Cliente save(@RequestBody @Valid Cliente cliente) {
		return service.save(cliente);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(NO_CONTENT)
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(OK)
	public Cliente update(@PathVariable Long id, @RequestBody @Valid Cliente cliente) {
		return service.update(id, cliente);
	}
	
	
}
