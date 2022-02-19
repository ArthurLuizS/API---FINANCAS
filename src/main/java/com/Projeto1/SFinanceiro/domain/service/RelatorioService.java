package com.Projeto1.SFinanceiro.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Projeto1.SFinanceiro.domain.model.Cliente;
import com.Projeto1.SFinanceiro.domain.model.Contas;
import com.Projeto1.SFinanceiro.domain.model.Endereco;
import com.Projeto1.SFinanceiro.domain.model.Relatorio;
import com.Projeto1.SFinanceiro.domain.model.RelatorioPeriodo;
import com.Projeto1.SFinanceiro.domain.model.RelatorioSaldo;
//import com.Projeto1.SFinanceiro.domain.model.Relatorio;
import com.Projeto1.SFinanceiro.domain.model.Transacoes;
import com.Projeto1.SFinanceiro.domain.repository.ClienteRepository;
import com.Projeto1.SFinanceiro.domain.repository.ContasRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RelatorioService {
	private BuscaContaService buscacontaService;
	private ClienteRepository clienteRepository;
	private ContasRepository contasRepository;
	public Relatorio relatorioIndividual(Long contaId) {
		
		Contas conta = buscacontaService.buscar(contaId);
		Float[] taxas = {(float) 1, (float) 0.75, (float) 0.5};
		Relatorio r = new Relatorio();
		Endereco e = conta.getCliente().getEndereco();
		//---------
		List<Transacoes> transacoesd = new ArrayList<>();
		List<Transacoes> transacoesc = new ArrayList<>();
		transacoesd = conta.getTransacoes().stream().filter(t -> t.getTipo() == 1).toList();
		transacoesc = conta.getTransacoes().stream().filter(t -> t.getTipo() == 2).toList();
		r.setMovimentacaoDebito(transacoesd.size());
		r.setMovimentacaoCredito(transacoesc.size());
		//---------
		//sets manuais
		r.setMovimentacoes(conta.getTransacoes().size());
	
		//r.setMovimentacaoDebito(conta.getTransacoes().);
		r.setCliente(conta.getCliente().getNome());
		r.setEndereco(e.getCidade().concat(" , ").concat(e.getBairro().concat(e.getLogradouro()) ));
		r.setData_cliente(conta.getCliente().getData_cliente());
		
		r.setSaldoInicial(conta.getTransacoes().get(0).getSaldo_inicial());
		r.setSaldoAtual(conta.getSaldo());
		
		
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

	public RelatorioPeriodo relatorioPeriodo(Long contaId, String dataInicio, 
			String dataFim) {
			
		return null;
	}

	public RelatorioSaldo relatorioSaldo() {
		
		return null;
	}
}
