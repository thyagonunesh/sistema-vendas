package com.estudos.vendas.entity;

import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.estudos.vendas.entity.pk.ItemPedidoPK;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_item_pedido")
public class ItemPedido {

	@EmbeddedId
	@JsonIgnore
	private ItemPedidoPK id = new ItemPedidoPK();
	private Integer quantidade;
	private BigDecimal preco;

	public ItemPedido(Pedido pedido, Produto produto, Integer quantidade, BigDecimal preco) {
		id.setPedido(pedido);
		id.setProduto(produto);
		this.quantidade = quantidade;
		this.preco = preco;
	}
	
	@JsonIgnore
	public Pedido getPedido() {
		return id.getPedido();
	}
	
	public void setPedido(Pedido pedido) {
		id.setPedido(pedido);
	}
	
	public Produto getProduto() {
		return id.getProduto();
	}
	
	public void setProduto(Produto produto) {
		id.setProduto(produto);
	}
	
}
