package com.Projeto1.SFinanceiro.api.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Projeto1.SFinanceiro.domain.model.Relatorio;
import com.Projeto1.SFinanceiro.domain.model.RelatorioPeriodo;
import com.Projeto1.SFinanceiro.domain.model.RelatorioPeriodoClientes;
import com.Projeto1.SFinanceiro.domain.model.RelatorioSaldo;
import com.Projeto1.SFinanceiro.domain.service.RelatorioService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("contas/relatorio/")
public class RelatorioController {
	
	
private RelatorioService relatorioService;
	
	@GetMapping("/{clienteId}")
	public Relatorio listar(@PathVariable Long clienteId) {
		
		return relatorioService.relatorioIndividual(clienteId);
	}
	
	@GetMapping("{clienteId}/p")
	public RelatorioPeriodo listarPeriodo(@PathVariable Long clienteId,
			@RequestBody String dataInicio, String dataFim) {
		
		return relatorioService.relatorioPeriodo(clienteId, dataInicio, dataFim);
	}
	
	@GetMapping
	public List<Object> listarTodos() {
		
		return relatorioService.relatorioSaldo();
		
	}
	@PostMapping
	public List<Object> receitaPeriodo(@RequestBody String dataIn /*, String dataFim*/) {
		dataIn.concat("T00:00:00-03:00");
		
		OffsetDateTime inicio = OffsetDateTime.parse(dataIn,
				DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss x"));
		
		//OffsetDateTime inicio = OffsetDateTime.parse();
		OffsetDateTime fim = OffsetDateTime.now();
		
		return relatorioService.RPReceita(inicio /*, fim*/);
		
	}
	
}