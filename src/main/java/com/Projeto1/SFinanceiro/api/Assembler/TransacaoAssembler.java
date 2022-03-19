package com.Projeto1.SFinanceiro.api.Assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.Projeto1.SFinanceiro.api.Model.TransacaoOutput;
import com.Projeto1.SFinanceiro.domain.model.Transacoes;

import lombok.AllArgsConstructor;
@Component
@AllArgsConstructor
public class TransacaoAssembler {
	public ModelMapper modelMapper;

	public TransacaoOutput toModel(Transacoes transacao) {
		
	return modelMapper.map(transacao, TransacaoOutput.class);
	}
}
	