package com.Projeto1.SFinanceiro.api.Assembler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.Projeto1.SFinanceiro.api.Model.ClienteOutput;
import com.Projeto1.SFinanceiro.api.Model.RelatorioOutput;
import com.Projeto1.SFinanceiro.api.Model.input.ClienteInput;
import com.Projeto1.SFinanceiro.api.Model.input.DataInput;
import com.Projeto1.SFinanceiro.domain.model.Cliente;
import com.Projeto1.SFinanceiro.domain.model.RelatorioPeriodoClientes;
import com.Projeto1.SFinanceiro.domain.model.RelatorioSaldo;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Component
public class RelatorioAssembler {

	public ModelMapper modelMapper;

	public RelatorioOutput toModel(RelatorioPeriodoClientes rpc) {
		
	return modelMapper.map(rpc, RelatorioOutput.class);
	}
	
	public List<RelatorioOutput> toCollectionModel (List<RelatorioPeriodoClientes> rpc){
		return rpc.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
	public Cliente toEntity(ClienteInput clienteInput) {
		return modelMapper.map(clienteInput, Cliente.class);
	}
	
	public List<String> RelatorioSaldoTodosClientes (List<RelatorioSaldo> relatorio){
		List<String> lista = new ArrayList<>();
		for(Integer i = 0; i < relatorio.size(); i++) {
			lista.add("Cliente: ".concat(relatorio.get(i).getCliente())
					.concat(" - Cliente desde: ").concat(relatorio.get(i).getDataCliente().toString())
					.concat(" - Saldo em: ").concat(relatorio.get(i).getDataSaldo().toString())
					.concat(" : ").concat(relatorio.get(i).getSaldo().toString()));
		}
		
		return lista;
	}
	
	public List<String> RelatorioPeriodoCliente (List<RelatorioPeriodoClientes> relatorio, DataInput dataInput){
		List<String> lista = new ArrayList<>();
		lista.add("Periodo: ".concat(dataInput.getDataInicio().concat(" a ")
				.concat(dataInput.getDataFim())));
		Integer x = 0;
		Float receita = 0F; 
		while (x < relatorio.size()) {
	 
			if(relatorio.get(x).getMovimentacoes() > 0) {
			lista.add("Cliente: ".concat(relatorio.get(x).getCliente()
					.concat("  - Quantidade de movimentações: "))
					.concat(relatorio.get(x).getMovimentacoes().toString())
					.concat(", Valor das movimentações: ")
					.concat(relatorio.get(x).getTaxas().toString()));
			receita = relatorio.get(x).getTaxas() + receita;
			}
			x++;
		
		}
		lista.add("Total de receitas: ".concat(receita.toString()));
		return  lista; 
	}
}

