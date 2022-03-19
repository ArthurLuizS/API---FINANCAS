package com.Projeto1.SFinanceiro.api.Model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.Projeto1.SFinanceiro.domain.model.Transacoes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContaOutput {
	private Long id;
	private String numeroConta;
	private Float saldo;
	private List<ListaTransacoes> transacoes;
	
	
}
