package com.estudos.vendas.entity.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

	@NotNull(message = "Informe o código do cliente.")
	private Long cliente;
	
	@NotNull(message = "Campo total é obrigatório.")
	private BigDecimal total;
	
	private List<ItemPedidoDTO> items;
	
}
