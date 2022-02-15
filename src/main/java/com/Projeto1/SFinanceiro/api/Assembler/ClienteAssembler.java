package com.Projeto1.SFinanceiro.api.Assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.Projeto1.SFinanceiro.api.Model.ClienteOutput;
import com.Projeto1.SFinanceiro.api.Model.input.ClienteInput;
import com.Projeto1.SFinanceiro.domain.model.Cliente;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ClienteAssembler {
	
	public ModelMapper modelMapper;

	public ClienteOutput toModel(Cliente cliente) {
		
	return modelMapper.map(cliente, ClienteOutput.class);
	}
	
	public List<ClienteOutput> toCollectionModel (List<Cliente> cliente){
		return cliente.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
	public Cliente toEntity(ClienteInput clienteInput) {
		return modelMapper.map(clienteInput, Cliente.class);
	}

}



