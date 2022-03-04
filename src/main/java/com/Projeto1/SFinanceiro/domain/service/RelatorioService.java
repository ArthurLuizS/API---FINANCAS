package com.Projeto1.SFinanceiro.domain.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

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
	
	@Transactional
	public Relatorio relatorioIndividual(Long clienteId) {
		Relatorio r = new Relatorio();
		
		
		Optional<Cliente> cliente = clienteRepository.findById(clienteId);
		cliente.get().getConta().forEach(conta -> {
			Endereco e = conta.getCliente().getEndereco();
			//---------
			List<Transacoes> transacoesd = new ArrayList<>();
			List<Transacoes> transacoesc = new ArrayList<>();
			transacoesd = conta.getTransacoes().stream().filter(t -> t.getTipo() == 1).toList();
			transacoesc = conta.getTransacoes().stream().filter(t -> t.getTipo() == 2).toList();
			r.setMovimentacaoDebito(transacoesd.size() + r.getMovimentacaoDebito());
			r.setMovimentacaoCredito(transacoesc.size()+ r.getMovimentacaoCredito() );
			//---------
			//sets manuais
			r.setCliente_Id(conta.getCliente().getId());
			//r.setValor(r.getValor());
			
			//r.setMovimentacoes(conta.getTransacoes().size() + r.getMovimentacoes());
		
			//r.setMovimentacaoDebito(conta.getTransacoes().);
			r.setCliente(conta.getCliente().getNome());
			r.setEndereco(e.getCidade().concat(" , ").concat(e.getBairro().concat(e.getLogradouro()) ));
			r.setData_cliente(conta.getCliente().getData_cliente());
			
			//r.setSaldoInicial(conta.getTransacoes().get(0).getSaldo_inicial());
			r.setSaldoAtual(conta.getSaldo()+r.getSaldoAtual());
			 
			//r.setValor(conta.getTaxas() + r.getValor());
			
			
			
			r.setTaxaCliente(conta.getTaxas() + r.getTaxaCliente());
			
		});
		
		//r.setTranss(cliente.get());		
		r.setSaldoInicial(cliente.get().getConta().get(0).getTransacoes().get(0).getSaldo_inicial());
		//r.setSaldoInicial(conta.getTransacoes().get(0).getSaldo_inicial());
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

	public RelatorioPeriodo relatorioPeriodo(Long contaId, String dataInicio, 
			String dataFim) {
			
		return null;
	}
	
	public List<Object> RPReceita(OffsetDateTime inicio /*, OffsetDateTime fim */) {
		List<Object> relatorio = new ArrayList<>();
		
		//--- Testando se a condico está sendo feita por data
		Cliente cliente2 = crudCliente.buscar(24L);
		OffsetDateTime inicio1 = cliente2.getData_cliente();
		OffsetDateTime fim = OffsetDateTime.now();
		//------------------------
		
			long x = 1L;
			while(x  <= clienteRepository.count()) {
			
				
				Cliente cliente1 = crudCliente.buscar(x);
				if(cliente1.getData_cliente().isBefore(fim) && cliente1.getData_cliente().isAfter(inicio) ) {
					
					RelatorioPeriodoClientes rpc = new RelatorioPeriodoClientes();
					rpc.setCliente(crudCliente.buscar(x).getNome());
					
					Cliente cliente = clienteRepository.getById(x);
					cliente.getConta().forEach(conta -> {
						rpc.setMovimentacoes(conta.getTransacoes().size() + rpc.getMovimentacoes());
						
					});
					rpc.setIdentificador(cliente.getIdentificador());
					rpc.setTaxas(cliente.getTaxa());
					
					relatorio.add(rpc);
				}
				x = x + 1L;
			}
			
			
			
		return relatorio;
	}
	

	public List<Object> relatorioSaldo() {
		long x = 1L;
		List<Object> relatorio = new ArrayList<>();

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
}
