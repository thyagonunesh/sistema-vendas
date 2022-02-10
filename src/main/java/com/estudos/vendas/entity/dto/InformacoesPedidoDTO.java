package com.estudos.vendas.entity.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformacoesPedidoDTO {

	private Long codigo;
	private String cpf;
	private String nomeCliente;
	private BigDecimal total;
	private String data;
	private String status;
	private List<InformacaoItemPedidoDTO> items;
	
}
