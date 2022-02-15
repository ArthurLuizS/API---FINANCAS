package com.Projeto1.SFinanceiro.api.Model;

import java.util.List;

import com.Projeto1.SFinanceiro.api.Assembler.ContaAssembler;
import com.Projeto1.SFinanceiro.domain.repository.ContasRepository;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteOutput {
	
	private Long id;
	private String nomeCliente;	
	private List<ContaOutput> conta;
	
	
}


