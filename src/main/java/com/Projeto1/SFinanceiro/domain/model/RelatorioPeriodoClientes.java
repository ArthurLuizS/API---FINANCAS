package com.Projeto1.SFinanceiro.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelatorioPeriodoClientes {
	
	private String cliente;
	private String identificador;
	private Integer movimentacoes = 0;
	private Float taxas;
	


}
