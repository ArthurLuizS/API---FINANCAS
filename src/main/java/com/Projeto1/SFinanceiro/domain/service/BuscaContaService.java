package com.Projeto1.SFinanceiro.domain.service;

import org.springframework.stereotype.Service;

import com.Projeto1.SFinanceiro.domain.exception.NegocioException;
import com.Projeto1.SFinanceiro.domain.model.Contas;
import com.Projeto1.SFinanceiro.domain.repository.ContasRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BuscaContaService {
	
	private ContasRepository contasRepository;
	
	public Contas buscar(long contaId) {
		return contasRepository.findById(contaId)
				.orElseThrow(() -> new NegocioException("Conta não encontrada"));
	}
}
