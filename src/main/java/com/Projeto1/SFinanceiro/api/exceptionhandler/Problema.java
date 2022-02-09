package com.Projeto1.SFinanceiro.api.exceptionhandler;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Problema {

	private Integer status;
	private OffsetDateTime datahora;
	private String titulo;
	
}
