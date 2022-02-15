package com.Projeto1.SFinanceiro.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.hibernate.transform.ToListResultTransformer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Projeto1.SFinanceiro.api.Assembler.ContaAssembler;
import com.Projeto1.SFinanceiro.api.Model.ContaOutput;
import com.Projeto1.SFinanceiro.api.Model.ListaTransacoes;
import com.Projeto1.SFinanceiro.api.Model.RelatorioOutput;
import com.Projeto1.SFinanceiro.domain.model.Contas;
import com.Projeto1.SFinanceiro.domain.model.Endereco;
import com.Projeto1.SFinanceiro.domain.model.Relatorio;
//import com.Projeto1.SFinanceiro.domain.model.Relatorio;
import com.Projeto1.SFinanceiro.domain.model.Transacoes;
import com.Projeto1.SFinanceiro.domain.repository.ContasRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RelatorioService {
	private BuscaContaService buscacontaService;
	
	public Relatorio numeroTransacoes(Long contaId) {
		
		Contas conta = buscacontaService.buscar(contaId);
		Float[] taxas = {(float) 1, (float) 0.75, (float) 0.5};
		Relatorio r = new Relatorio();
		Endereco e = conta.getCliente().getEndereco();
		//sets manuais
		r.setMovimentacoes(conta.getTransacoes().size());
		r.setCliente(conta.getCliente().getNome());
		r.setEndereco(e.getCidade().concat(" , ").concat(e.getBairro().concat(e.getLogradouro()) ));
		r.setData_cliente(conta.getCliente().getData_cliente());
		
		
		//
		if(r.getMovimentacoes() <= 10) {
			r.setValorTransacoes(r.getMovimentacoes() * taxas[0]);
		}else if(r.getMovimentacoes() > 10 && r.getMovimentacoes() <= 20) {
			r.setValorTransacoes(r.getMovimentacoes() * taxas[1]);
		}else if (r.getMovimentacoes() > 20) {
			r.setValorTransacoes(r.getMovimentacoes() * taxas[2]);
		} else {
			throw new Error("Nenhuma transação nesta conta");
		}
		//boolean relatorio = conta.getTransacoes().contains("cr");	
		// pegar a conta / contar as transacoes da conta e retornar
		/*
		 * 
		 * if(tipoMovimentacao.contains("de") || tipoMovimentacao.contains("De") ) {
				nvalor = avalor - valor;
			}else if(tipoMovimentacao.contains("cr") || tipoMovimentacao.contains("Cr")) {
				nvalor = avalor + valor;
			}
		 * 
		 * fazer com que retorne um valor de acordo com a quantidade de transaçoes
		 * por periodo de 30 dias
		 * 
		 * receber o id do cliente e retornar TODAS as movimentações junto com os dados do cliente
		 * 
		 * fazer o calculo de descnto por cada movimentação
		 * */
		return r  ;
	}
}
