package com.Projeto1.SFinanceiro.api.Model;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListaTransacoes {

	private Float valor;
	private String tipoMovimentacao;
	private OffsetDateTime data;
	
}
