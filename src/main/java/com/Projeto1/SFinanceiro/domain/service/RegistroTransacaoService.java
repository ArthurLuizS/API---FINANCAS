package com.Projeto1.SFinanceiro.domain.service;

import java.math.BigDecimal;

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
	public Transacoes registrar(Long contaId, String tipoMovimentacao, BigDecimal valor) {
			Contas conta = buscaContaService.buscar(contaId);
		
		 return conta.efetuarTransacao(tipoMovimentacao, valor);
	}
}
