package com.Projeto1.SFinanceiro.api.Model.input;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteInput {
	private String nome;
	private String tipo_cliente;
	private String identificador;
	private String telefone;
	private EnderecoInput endereco;
	private OffsetDateTime data_cliente;
	private ContaInput contas;
	
}
