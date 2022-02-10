package com.estudos.vendas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import com.estudos.vendas.entity.Produto;
import com.estudos.vendas.exceptions.NaoEncontradoException;
import com.estudos.vendas.repositories.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repository;
	
	public List<Produto> find(Produto filtro){
		ExampleMatcher matcher = ExampleMatcher
									.matching()
									.withIgnoreCase()
									.withStringMatcher(StringMatcher.CONTAINING);
		
		Example<Produto> example = Example.of(filtro, matcher);
		return repository.findAll(example);
	}
	
	public Produto findById(Long id) {
		return repository
				.findById(id)
				.orElseThrow( () -> new NaoEncontradoException("Produto não encotrado"));
	}
	
	public Produto save(Produto produto) {
		return repository.save(produto);
	}
	
	public void delete(Long id) {
		repository
			.findById(id)
			.map(produto -> {
				repository.delete(produto);
				return produto;
			}).orElseThrow( () -> new NaoEncontradoException("Produto não encotrado"));
	}
	
	public Produto update(Long id, Produto produto) {
		return repository
					.findById(id)
					.map( produtoExistente -> {
						produto.setId(produtoExistente.getId());
						repository.save(produto);
						return produto;
					}).orElseThrow( () -> new NaoEncontradoException( "Produto não encotrado"));
	}
	
}
