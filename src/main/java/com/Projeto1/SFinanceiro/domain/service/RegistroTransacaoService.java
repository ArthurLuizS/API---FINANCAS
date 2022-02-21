package com.Projeto1.SFinanceiro.domain.service;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Projeto1.SFinanceiro.domain.model.Contas;
import com.Projeto1.SFinanceiro.domain.model.Transacoes;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RegistroTransacaoService {

	private BuscaContaService buscaContaService;
	
	@Transactional
	public Transacoes registrar(Long contaId, String tipoMovimentacao, Float valor) {
			Contas conta = buscaContaService.buscar(contaId);
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
			
			Float[] taxas = {(float) 1, (float) 0.75, (float) 0.5};
			if(conta.getTransacoes().size() <= 9) {
				conta.setTaxas(conta.getTaxas() + taxas[0]);
			
			}else if(conta.getTransacoes().size() > 9 && conta.getTransacoes().size() <= 19) {
				conta.setTaxas(conta.getTaxas() + taxas[1]);
				
				
			}else if (conta.getTransacoes().size() > 19) {
				conta.setTaxas(conta.getTaxas() + taxas[2]);
				
			}
		
		 return conta.efetuarTransacao(tipoMovimentacao, valor, avalor, tipo);
	}
}
