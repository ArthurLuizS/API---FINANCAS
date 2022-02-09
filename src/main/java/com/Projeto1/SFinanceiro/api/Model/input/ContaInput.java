package com.Projeto1.SFinanceiro.api.Model.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContaInput {
	private ClienteIdInput cliente;
	private String numeroConta;
	private String tipoMovimentacao;
	private Float saldo;
	
}
