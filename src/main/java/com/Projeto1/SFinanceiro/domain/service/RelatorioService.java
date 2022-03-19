package com.Projeto1.SFinanceiro.domain.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.Projeto1.SFinanceiro.domain.model.Cliente;
import com.Projeto1.SFinanceiro.domain.model.Contas;
import com.Projeto1.SFinanceiro.domain.model.Endereco;
import com.Projeto1.SFinanceiro.domain.model.Relatorio;
import com.Projeto1.SFinanceiro.domain.model.RelatorioPeriodo;
import com.Projeto1.SFinanceiro.domain.model.RelatorioPeriodoClientes;
import com.Projeto1.SFinanceiro.domain.model.RelatorioSaldo;
//import com.Projeto1.SFinanceiro.domain.model.Relatorio;
import com.Projeto1.SFinanceiro.domain.model.Transacoes;
import com.Projeto1.SFinanceiro.domain.repository.ClienteRepository;
import com.Projeto1.SFinanceiro.domain.repository.ContasRepository;
import com.Projeto1.SFinanceiro.domain.repository.RelatorioRepository;
import com.Projeto1.SFinanceiro.domain.repository.TransacoesRepository;

import ch.qos.logback.core.filter.Filter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RelatorioService {
	private BuscaContaService buscacontaService;
	private ClienteRepository clienteRepository;
	private ContasRepository contasRepository;
	private RelatorioRepository relatorioRepository;
	private ContasService contaService;
	private CrudCliente crudCliente;
	private TransacoesRepository transacoesRepository;
	
	@Transactional
	public Relatorio relatorioIndividual(Long clienteId) {
		Relatorio r = new Relatorio();
			
		Optional<Cliente> cliente = clienteRepository.findById(clienteId);
		cliente.get().getConta().forEach(conta -> {
			Endereco e = conta.getCliente().getEndereco();
			List<Transacoes> transacoesd = new ArrayList<>();
			List<Transacoes> transacoesc = new ArrayList<>();
			transacoesd = conta.getTransacoes().stream().filter(t -> t.getTipo() == 1).toList();
			transacoesc = conta.getTransacoes().stream().filter(t -> t.getTipo() == 2).toList();
			//---------
			//sets manuais
			r.setMovimentacaoDebito(transacoesd.size() + r.getMovimentacaoDebito());
			r.setMovimentacaoCredito(transacoesc.size()+ r.getMovimentacaoCredito() );
			r.setCliente_Id(conta.getCliente().getId());
			r.setCliente(conta.getCliente().getNome());
			r.setEndereco(e.getCidade().concat(" , ").concat(e.getBairro().concat(" , ").concat(e.getLogradouro()) ));
			r.setData_cliente(conta.getCliente().getData_cliente());
			r.setSaldoAtual(conta.getSaldo()+r.getSaldoAtual());
			 	
		});
		r.setTaxaCliente(cliente.get().getTaxa());				
		r.setSaldoInicial(cliente.get().getConta().get(0).getTransacoes().get(0).getSaldo_inicial());
	
		r.setQuantidadeContas(cliente.get().getConta().size());
		/*
		
		
		 * 
		 * fazer com que retorne um valor de acordo com a quantidade de transaçoes
		 * por periodo de 30 dias
		 * 
		 * receber o id do cliente e retornar TODAS as movimentações junto com os dados do cliente
		 * 
		 * fazer o calculo de descnto por cada movimentação
		 * 
		 * 
		 * */
		
		r.setMovimentacoes(r.getMovimentacaoCredito() + r.getMovimentacaoDebito());
		
		return r  ;
	}
	//----------------------------------------------------------//
	//----------------------------------------------------------//
	public List<RelatorioPeriodoClientes> RPReceita(OffsetDateTime inicio , OffsetDateTime fim ) {
		List<RelatorioPeriodoClientes> relatorio = new ArrayList<>();
		List<Transacoes> transacoes = new ArrayList<>();
		Integer x = 0;
		Long y = 1L;
		
	while(x < transacoesRepository.count()) {
		
		Transacoes transacao = transacoesRepository.getById(y);
		if (transacao.getData().isAfter(inicio) && transacao.getData().isBefore(fim)) {
			transacoes.add(transacao);
		}
		x++;
		y++;
	} 
	for(Long a = 1L ; a <= clienteRepository.count() ; a++ ) {
		Cliente cliente = crudCliente.buscar(a);
		if(transacoes.contains(cliente.getId())) {
		}
			
		List<Transacoes> resumo = transacoes.stream().filter(t ->
		t.getConta().getCliente().getId() == cliente.getId())
		.collect(Collectors.toList());
		RelatorioPeriodoClientes rpc = new RelatorioPeriodoClientes();
		rpc.setMovimentacaoCredito(resumo.stream().filter(t -> t.getTipo() == 1).collect(Collectors.toList()).size());
		rpc.setCliente(cliente.getNome());
		rpc.setIdentificador(cliente.getIdentificador());
		rpc.setMovimentacoes(resumo.size());
				
		if(rpc.getMovimentacoes() <= 10) {
			rpc.setTaxas(rpc.getMovimentacoes().floatValue() * 1);
		}else if(rpc.getMovimentacoes() > 10 && rpc.getMovimentacoes() <= 20 ) {
			Float sobra = rpc.getMovimentacoes().floatValue() - 10;
			rpc.setTaxas(10F + sobra * 0.75F);
		}else {
			Float sobra = rpc.getMovimentacoes().floatValue() - 20;
			rpc.setTaxas(20F + sobra * 0.5F );
		}	
		
		relatorio.add(rpc);
		
		}
	
		return relatorio;
	}
	
	//----------------------------------------------------------//
	//----------------------------------------------------------//
	public List<RelatorioSaldo> relatorioSaldo() {
		long x = 1L;
		List<RelatorioSaldo> relatorio = new ArrayList<>();

		while(x  <= clienteRepository.count()) {
			RelatorioSaldo relatorioSaldo = new RelatorioSaldo();
		 
			relatorioSaldo.setCliente(crudCliente.buscar(x).getNome());
			relatorioSaldo.setDataCliente(clienteRepository.getById(x).getData_cliente());
			relatorioSaldo.setDataSaldo(OffsetDateTime.now());
			
			Cliente cliente = clienteRepository.getById(x);
			 cliente.getConta().forEach(conta -> {
				relatorioSaldo.setSaldo(conta.getSaldo() + relatorioSaldo.getSaldo());
			});
			
			relatorio.add(relatorioSaldo); 
			x = x + 1L;		
		}
		
		return relatorio;
		
	}
	
	public RelatorioPeriodoClientes relatorioPeriodo(Long clienteId, OffsetDateTime dataInicio, 
			OffsetDateTime dataFim) {
			RelatorioPeriodoClientes rpc = new RelatorioPeriodoClientes();
			Cliente cliente = crudCliente.buscar(clienteId);
			
			rpc.setCliente(cliente.getNome());
			rpc.setData_cliente(cliente.getData_cliente());
			rpc.setEndereco(relatorioIndividual(clienteId).getEndereco());
			
			List<Contas> contas = cliente.getConta().stream().filter(c ->
			c.getDataConta().isAfter(dataInicio) && c.getDataConta().isBefore(dataFim)).toList();
			
			List<Transacoes> transacoes = new ArrayList<>();
			List<Transacoes> transacoesFiltradas = new ArrayList<>();
			
		contas.forEach(c -> 
		transacoes.addAll(c.getTransacoes()));
		//------------------------------
		//FILTRANDO TRANSACOES PELAS DATAS
		transacoes.forEach(t -> {
		if(t.getData().isAfter(dataInicio) && t.getData().isBefore(dataFim)){
			transacoesFiltradas.add(t);	
		}});
		//-------------------------------
	
			Long credito = transacoesFiltradas.stream().filter(t -> t.getTipo() == 2).count();
			Long debito = transacoesFiltradas.stream().filter(t -> t.getTipo() == 1).count();
			rpc.setMovimentacaoCredito(credito.intValue());
			rpc.setMovimentacaoDebito(debito.intValue());
			rpc.setMovimentacoes(rpc.getMovimentacaoCredito() + rpc.getMovimentacaoDebito());
			rpc.setSaldoInicial(transacoesFiltradas.get(1).getSaldo_inicial());	
			rpc.setSaldoAtual(relatorioIndividual(cliente.getId()).getSaldoAtual());
			
			if(rpc.getMovimentacoes() <= 10) {
				rpc.setTaxas(rpc.getMovimentacoes().floatValue() * 1);
			}else if(rpc.getMovimentacoes() > 10 && rpc.getMovimentacoes() <= 20 ) {
				Float sobra = rpc.getMovimentacoes().floatValue() - 10;
				rpc.setTaxas(10F + sobra * 0.75F);
			}else {
				Float sobra = rpc.getMovimentacoes().floatValue() - 20;
				rpc.setTaxas(20F + sobra * 0.5F );
			};
			
	return  rpc;
	}
}
