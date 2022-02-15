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
			Float avalor = conta.getSaldo();
			Float nvalor = null;
			if(tipoMovimentacao.contains("de") || tipoMovimentacao.contains("De") ) {
				nvalor = avalor - valor;
			}else if(tipoMovimentacao.contains("cr") || tipoMovimentacao.contains("Cr")) {
				nvalor = avalor + valor;
			}
			
			conta.setSaldo(nvalor);
		
		 return conta.efetuarTransacao(tipoMovimentacao, valor, avalor);
	}
}
