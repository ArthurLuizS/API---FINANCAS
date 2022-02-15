package com.Projeto1.SFinanceiro.api.Model.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoInput {
	private String logradouro;
	private String numero;
	private String bairro;
	private String cidade;
}
