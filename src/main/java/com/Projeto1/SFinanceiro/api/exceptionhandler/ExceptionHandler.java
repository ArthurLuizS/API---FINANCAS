package com.Projeto1.SFinanceiro.api.exceptionhandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import com.Projeto1.SFinanceiro.domain.exception.NegocioException;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setDatahora(OffsetDateTime.now());
		problema.setTitulo("Um ou mais campos ivalidos");
		
	return handleExceptionInternal(ex, problema, headers, status, request);
	
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleNegocio (NegocioException ex, WebRequest request){
		HttpStatus status = HttpStatus.BAD_REQUEST; 
		
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setDatahora(OffsetDateTime.now());
		problema.setTitulo(ex.getMessage());
		
		return handleExceptionInternal(ex, problema, null, status, request);
	}
	
}


