package com.estudos.vendas.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import com.estudos.vendas.entity.Cliente;
import com.estudos.vendas.entity.ItemPedido;
import com.estudos.vendas.entity.Pedido;
import com.estudos.vendas.entity.Produto;
import com.estudos.vendas.entity.dto.ItemPedidoDTO;
import com.estudos.vendas.entity.dto.PedidoDTO;
import com.estudos.vendas.entity.enums.StatusPedido;
import com.estudos.vendas.exceptions.NaoEncontradoException;
import com.estudos.vendas.repositories.ClienteRepository;
import com.estudos.vendas.repositories.ItemPedidoRepository;
import com.estudos.vendas.repositories.PedidoRepository;
import com.estudos.vendas.repositories.ProdutoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Transactional
	public Pedido save(PedidoDTO dto) {
		Pedido pedido = new Pedido();
		Cliente cliente = clienteRepository
							.findById(dto.getCliente())
							.orElseThrow(() -> new NaoEncontradoException("Cliente não encontrado"));
		pedido.setCliente(cliente);
		pedido.setDataPedido(Instant.now());
		pedido.setTotal(dto.getTotal());
		pedido.setStatus(StatusPedido.REALIZADO);
		
		List<ItemPedido> items = converterItems(dto.getItems());
		
		items.forEach(i -> i.setPedido(pedido));
		pedido.setItems(items);

		pedidoRepository.save(pedido);
		itemPedidoRepository.saveAll(items);
		
		return pedido;
	}
	
	public List<Pedido> find(Pedido filtro){
		ExampleMatcher matcher = ExampleMatcher
									.matching()
									.withIgnoreCase()
									.withStringMatcher(StringMatcher.CONTAINING);
		
		Example<Pedido> example = Example.of(filtro, matcher);
		return pedidoRepository.findAll(example);
	}
	
	public Pedido findById(Long id) {
		return pedidoRepository
				.findById(id)
				.orElseThrow( () -> new NaoEncontradoException("Pedido não encotrado"));
	}
	
	@Transactional
	public void delete(Long id) {
		pedidoRepository
			.findById(id)
			.map(pedido -> {
				pedidoRepository.delete(pedido);
				return pedido;
			}).orElseThrow( () -> new NaoEncontradoException("Pedido não encotrado"));
	}
	
	@Transactional
	public Pedido update(Long id, Pedido pedido) {
		return pedidoRepository
					.findById(id)
					.map( pedidoExistente -> {
						pedido.setId(pedidoExistente.getId());
						pedidoRepository.save(pedido);
						return pedido;
					}).orElseThrow( () -> new NaoEncontradoException( "Pedido não encotrado"));
	}
	
	public Optional<Pedido> obterPedidoCompleto(Long id) {
		return pedidoRepository.findByIdFetchItems(id);
	}
	
    @Transactional
    public void atualizaStatus (Long id, StatusPedido statusPedido ) {
        pedidoRepository
                .findById(id)
                .map( pedido -> {
                    pedido.setStatus(statusPedido);
                    return pedidoRepository.save(pedido);
                }).orElseThrow(() -> new NaoEncontradoException("Pedido não encotrado") );
    }
	
	private List<ItemPedido> converterItems(List<ItemPedidoDTO> dto) {
		if (dto.isEmpty()) {
            throw new NaoEncontradoException("Não é possível realizar um pedido sem items.");
		}
		
		return dto
				.stream()
				.map(itemDto -> {
					ItemPedido item = new ItemPedido();
					
					Produto produto = produtoRepository
										.findById(itemDto.getProduto())
										.orElseThrow( () -> new IllegalArgumentException("Produto não encontrado"));
		
					item.setProduto(produto);
					
					item.setPreco(itemDto.getPreco());
					item.setQuantidade(itemDto.getQuantidade());
					
					return item;
		}).collect(Collectors.toList());
	}
	
}