package com.estudos.vendas.exceptions;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor	
public class DefaultError {

	private Instant timestamp;
	private Integer status;
	private String error;
	private String message;
	private String path;
	
}
