package com.Projeto1.SFinanceiro.api.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Projeto1.SFinanceiro.api.Assembler.RelatorioAssembler;
import com.Projeto1.SFinanceiro.api.Model.RelatorioOutput;
import com.Projeto1.SFinanceiro.api.Model.input.DataInput;
import com.Projeto1.SFinanceiro.domain.model.Cliente;
import com.Projeto1.SFinanceiro.domain.model.Contas;
import com.Projeto1.SFinanceiro.domain.model.Relatorio;
import com.Projeto1.SFinanceiro.domain.model.RelatorioPeriodo;
import com.Projeto1.SFinanceiro.domain.model.RelatorioPeriodoClientes;
import com.Projeto1.SFinanceiro.domain.model.RelatorioSaldo;
import com.Projeto1.SFinanceiro.domain.model.Transacoes;
import com.Projeto1.SFinanceiro.domain.repository.ClienteRepository;
import com.Projeto1.SFinanceiro.domain.service.CrudCliente;
import com.Projeto1.SFinanceiro.domain.service.RelatorioService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("contas/relatorio/")
public class RelatorioController {
	
	RelatorioAssembler relatorioAssembler;
	ClienteRepository clienteRepository;
	
	
private RelatorioService relatorioService;
	
	@GetMapping("/{clienteId}")
	public Relatorio listar(@PathVariable Long clienteId) {
		
		return relatorioService.relatorioIndividual(clienteId);
	}
	
	@PostMapping("{clienteId}/periodo")
	public List<String> listarPeriodo(@PathVariable Long clienteId,
			@RequestBody DataInput dataInput) {
		OffsetDateTime inicio = OffsetDateTime.parse(dataInput.getDataInicio().concat("T00:00:00.246+00:00"));
		OffsetDateTime fim = OffsetDateTime.parse(dataInput.getDataFim().concat("T23:59:59.246+00:00"));
		
		
		RelatorioPeriodoClientes rpc =  relatorioService.relatorioPeriodo(clienteId, inicio, fim);
		
		List<String> relatorio = new ArrayList<>();
		relatorio.add("Periodo: ".concat(dataInput.getDataInicio().concat(" a ")
				.concat(dataInput.getDataFim())));
		relatorio.add("Cliente: ".concat(rpc.getCliente()));
		relatorio.add("Cliente desde: ".concat(rpc.getData_cliente().toString()));
		relatorio.add("Endereço: ".concat(rpc.getEndereco()));
		relatorio.add("Movimentacoes de crédito: ".concat(rpc.getMovimentacaoCredito().toString()));
		relatorio.add("Movimentações de débito: ".concat(rpc.getMovimentacaoDebito().toString()));
		relatorio.add("Total de movimentações: ".concat(rpc.getMovimentacoes().toString()));
		relatorio.add("Valor pago pelas movimentações: ".concat(rpc.getTaxas().toString()));
		relatorio.add("Saldo inicial: ".concat(rpc.getSaldoInicial().toString()));
		relatorio.add("Saldo atual: ".concat(rpc.getSaldoAtual().toString()));


		//relatorio.add(rpc.getClass().toString());
		
		
		
		return relatorio;
	}
	
	@GetMapping
	public List<String> listarTodos() {
		/*Relatório de saldo de todos os clientes;
			Cliente: X - Cliente desde: DD/MM/YYYY – Saldo em DD/MM/YYYY: 0.000,00
			Cliente: Y - Cliente desde: DD/MM/YYYY - Saldo em DD/MM/YYYY: 000,00
			Cliente: Z - Cliente desde: DD/MM/YYYY - Saldo em DD/MM/YYYY: 00,00
		 */
		
		return relatorioAssembler.RelatorioSaldoTodosClientes(relatorioService.relatorioSaldo()) ;
		
	}
	@PostMapping
	//public List<RelatorioPeriodoClientes> receitaPeriodo(@RequestBody DataInput dataInput) {
		public List<String> receitaPeriodo(@RequestBody DataInput dataInput) {
		
		OffsetDateTime inicio = OffsetDateTime.parse(dataInput.getDataInicio().concat("T00:00:00.246+00:00"));
		OffsetDateTime fim = OffsetDateTime.parse(dataInput.getDataFim().concat("T23:59:59.246+00:00"));
		
		return  relatorioAssembler.RelatorioPeriodoCliente(relatorioService.RPReceita(inicio, fim), dataInput);  
  
	}
	
}