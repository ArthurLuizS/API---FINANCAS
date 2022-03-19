package com.Projeto1.SFinanceiro.domain.service;

import java.time.OffsetDateTime;
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
import com.Projeto1.SFinanceiro.domain.repository.ClienteRepository;
import com.Projeto1.SFinanceiro.domain.repository.TransacoesRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RegistroTransacaoService {

	private BuscaContaService buscaContaService;
	private TransacoesRepository transacoesRepository;
	private ClienteRepository clienteRepository;
	
	@Transactional
	public Transacoes registrar(Long contaId, String tipoMovimentacao, Float valor) {
			Contas conta = buscaContaService.buscar(contaId);
			Cliente cliente = clienteRepository.getById(conta.getCliente().getId());
			

	
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
			
		
			//--------Fazer com que as taxas sejam resetadas a cada 30Dias----------
			String data = "2022-03-28";
			OffsetDateTime dataManipulada = OffsetDateTime.parse(data
					.concat("T00:01:11.246-03:00"));
			
			if(OffsetDateTime.now().compareTo(cliente.getParametro()) < 30) {
				cliente.setParametroTrans(cliente.getParametroTrans() + 1);
			}else {
				cliente.setParametro(cliente.getParametro().plusDays(30));
				cliente.setParametroTrans(1);
			}
	
			if(cliente.getParametroTrans() <= 10) {
				conta.setTaxas(conta.getTaxas() + 1);
				cliente.setTaxa(cliente.getTaxa() + 1);
			} else if (cliente.getParametroTrans() > 10 && cliente.getParametroTrans() <= 20 ) {
				conta.setTaxas(conta.getTaxas()+ 0.75F);
				cliente.setTaxa(cliente.getTaxa() +  0.75F);
			}else {
				conta.setTaxas(conta.getTaxas() +  0.5F);
				cliente.setTaxa(cliente.getTaxa() + 0.5F);
			}

			//------------------
	
		 return conta.efetuarTransacao(tipoMovimentacao, valor, avalor, tipo);
	}
	
}
