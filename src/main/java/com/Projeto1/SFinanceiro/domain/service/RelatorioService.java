package com.Projeto1.SFinanceiro.domain.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

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
	
	public List<RelatorioPeriodoClientes> RPReceita(OffsetDateTime inicio , OffsetDateTime fim ) {
		List<RelatorioPeriodoClientes> relatorio = new ArrayList<>();
		List<Transacoes> transacoes = new ArrayList<>();
		//------------------------
		List<Contas> contas = new ArrayList<>();
		Integer x = 0;
		Long y = 1L;
		Integer z = 0;
		
	while(x < transacoesRepository.count()) {
/*		Contas conta = contasRepository.getById(y);
		if(conta.getDataConta().isAfter(inicio)
				&& conta.getDataConta().isBefore(fim)) {
			contas.add(conta);
		}
		
		*/
		
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

			
			
	//	return transacoes;	
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
