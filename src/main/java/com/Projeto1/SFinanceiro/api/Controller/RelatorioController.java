package com.Projeto1.SFinanceiro.api.Controller;

import java.time.OffsetDateTime;
import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Projeto1.SFinanceiro.domain.model.Relatorio;
import com.Projeto1.SFinanceiro.domain.model.RelatorioPeriodo;
import com.Projeto1.SFinanceiro.domain.model.RelatorioSaldo;
import com.Projeto1.SFinanceiro.domain.service.RelatorioService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("contas/relatorio/")
public class RelatorioController {
	
	
private RelatorioService relatorioService;
	
	@GetMapping("/{contaId}")
	public Relatorio listar(@PathVariable Long contaId) {
		
		return relatorioService.relatorioIndividual(contaId);
	}
	
	@GetMapping("{contaId}/p")
	public RelatorioPeriodo listarPeriodo(@PathVariable Long contaId,
			@RequestBody String dataInicio, String dataFim) {
		
		return relatorioService.relatorioPeriodo(contaId, dataInicio, dataFim);
	}
	
	@GetMapping
	public RelatorioSaldo listarTodos() {
		
		return relatorioService.relatorioSaldo();
		
	}
}