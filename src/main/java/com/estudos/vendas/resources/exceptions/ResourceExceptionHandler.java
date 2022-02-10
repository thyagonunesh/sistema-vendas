package com.estudos.vendas.resources.exceptions;

import java.time.Instant;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.estudos.vendas.exceptions.DefaultError;
import com.estudos.vendas.exceptions.NaoEncontradoException;

@RestControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(NaoEncontradoException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public DefaultError handlerNaoEncontradoException(NaoEncontradoException e, HttpServletRequest request) {
		return new DefaultError(
				Instant.now(),
				HttpStatus.NOT_FOUND.value(),
				"Não encontrado",
				e.getMessage(),
				request.getRequestURI()
			);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public DefaultError handlerMethodNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {		
		return new DefaultError(
				Instant.now(),
				HttpStatus.BAD_REQUEST.value(),
				e.getBindingResult().getAllErrors().stream().map(er -> er.getDefaultMessage()).collect(Collectors.toList()).toString(),
				"Revise os dados de requisição.",
				request.getRequestURI()
			);
	}
	
}
