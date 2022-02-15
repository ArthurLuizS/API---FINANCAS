package com.Projeto1.SFinanceiro.api.Assembler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.Projeto1.SFinanceiro.api.Model.ContaOutput;
import com.Projeto1.SFinanceiro.api.Model.input.ContaInput;
import com.Projeto1.SFinanceiro.domain.model.Contas;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Component
public class ContaAssembler {
	
	public ModelMapper modelMapper;

	public ContaOutput toModel(Contas conta) {
		
	return modelMapper.map(conta, ContaOutput.class);
	}
	
	public List<ContaOutput> toCollectionModel (List<Contas> contas){
		return contas.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
	public Contas toEntity(ContaInput contaInput) {
		
		return modelMapper.map(contaInput, Contas.class);
	}

}
