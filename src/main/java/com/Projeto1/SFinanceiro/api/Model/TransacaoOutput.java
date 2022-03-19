package com.Projeto1.SFinanceiro.api.Model;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoOutput {
	private Long id;
	private Long idConta;
	private String tipo;
	private String numeroConta;
	private Float valor;
	private OffsetDateTime data;
}
