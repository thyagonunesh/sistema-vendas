package com.estudos.vendas.resources;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.estudos.vendas.entity.ItemPedido;
import com.estudos.vendas.entity.Pedido;
import com.estudos.vendas.entity.dto.AtualizacaoStatusPedidoDTO;
import com.estudos.vendas.entity.dto.InformacaoItemPedidoDTO;
import com.estudos.vendas.entity.dto.InformacoesPedidoDTO;
import com.estudos.vendas.entity.dto.PedidoDTO;
import com.estudos.vendas.entity.enums.StatusPedido;
import com.estudos.vendas.exceptions.NaoEncontradoException;
import com.estudos.vendas.services.PedidoService;

@RestController
@RequestMapping("api/pedidos")
public class PedidoResource {

	@Autowired
	private PedidoService service;
	
	@PostMapping
	@ResponseStatus(CREATED)
	public Pedido save(@RequestBody @Valid PedidoDTO dto) {
		return service.save(dto);
	}
	
	@GetMapping
	public List<Pedido> find(Pedido filtro){
		return service.find(filtro);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(NO_CONTENT)
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(OK)
	public Pedido update(@PathVariable Long id, @RequestBody Pedido pedido) {
		return service.update(id, pedido);
	}
	
	@GetMapping("/{id}")
	public InformacoesPedidoDTO findById(@PathVariable Long id) {
		return service
					.obterPedidoCompleto(id)
					.map( p -> converter(p))
					.orElseThrow( () -> new NaoEncontradoException("Pedido não encontrado"));
	}
	
	
    @PatchMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateStatus(@PathVariable Long id ,
                             @RequestBody AtualizacaoStatusPedidoDTO dto){
        String novoStatus = dto.getNovoStatus();
        service.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
    }
	
	private InformacoesPedidoDTO converter(Pedido pedido) {
		return InformacoesPedidoDTO
				.builder()
				.codigo(pedido.getId())
				.data(pedido.getDataPedido().toString())
				.cpf(pedido.getCliente().getCpf())
				.nomeCliente(pedido.getCliente().getNome())
				.total(pedido.getTotal())
				.status(pedido.getStatus().toString())
				.items(converter(pedido.getItems()))
				.build();
	}
	
	private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> items) {
		if (CollectionUtils.isEmpty(items)) {
			return Collections.emptyList();
		}
		return items.stream().map(
				item -> InformacaoItemPedidoDTO
						.builder()
						.descricaoProduto(item.getProduto().getDescricao())
						.precoUnitario(item.getPreco())
						.quantidade(item.getQuantidade())
						.build()
				).collect(Collectors.toList());
	}
	
}
