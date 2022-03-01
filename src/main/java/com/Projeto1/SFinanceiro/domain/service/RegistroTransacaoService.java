package com.Projeto1.SFinanceiro.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Projeto1.SFinanceiro.domain.model.Cliente;
import com.Projeto1.SFinanceiro.domain.model.Contas;
import com.Projeto1.SFinanceiro.domain.model.Transacoes;
import com.Projeto1.SFinanceiro.domain.repository.TransacoesRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RegistroTransacaoService {

	private BuscaContaService buscaContaService;
	private TransacoesRepository transacoesRepository;
	
	@Transactional
	public Transacoes registrar(Long contaId, String tipoMovimentacao, Float valor) {
			Contas conta = buscaContaService.buscar(contaId);
			Cliente cliente = conta.getCliente();
			

	
			Integer tipo = null;
			Float avalor = conta.getSaldo();
			Float nvalor = null;
			if(conta.getSaldo() >= valor && tipoMovimentacao.contains("de") || tipoMovimentacao.contains("De") ) {
				nvalor = avalor - valor;
				tipo = 1;
			}else if(tipoMovimentacao.contains("cr") || tipoMovimentacao.contains("Cr")) {
				nvalor = avalor + valor;
				tipo = 2;
			}else {
				 throw new Error("saldo Insuficiente, saldo atual : ".concat(conta.getSaldo().toString()));
			}
			
			conta.setSaldo(nvalor);
			
			
			//------------------
			Float[] taxas = {(float) 1, (float) 0.75, (float) 0.5};
		
			cliente.getConta().forEach(c -> {
				cliente.setTransQtd(cliente.getTransQtd() + c.getTransacoes().size());
				
			});
			
			if(cliente.getTransQtd() <= 9 ) {
				conta.setTaxas(conta.getTaxas() + taxas[0]);
				cliente.setTaxa(cliente.getTaxa() + taxas[0]);
				
			}else if(cliente.getTransQtd() > 9 && cliente.getTransQtd()<=19) {
				conta.setTaxas(conta.getTaxas() + taxas[1]);
				cliente.setTaxa(cliente.getTaxa() + taxas[1]);
				
			}else if (cliente.getTransQtd() > 19) {
				conta.setTaxas(conta.getTaxas() + taxas[2]);
				cliente.setTaxa(cliente.getTaxa() + taxas[2]);
			}
		
		 return conta.efetuarTransacao(tipoMovimentacao, valor, avalor, tipo);
	}
}
