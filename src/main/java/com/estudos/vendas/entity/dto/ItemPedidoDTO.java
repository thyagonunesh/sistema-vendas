package com.estudos.vendas.entity.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoDTO {

	private Long produto;
	private Integer quantidade;
	private BigDecimal preco;
	
}
