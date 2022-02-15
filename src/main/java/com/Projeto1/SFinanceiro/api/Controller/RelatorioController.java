package com.Projeto1.SFinanceiro.api.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.Projeto1.SFinanceiro.api.Model.ListaTransacoes;
import com.Projeto1.SFinanceiro.api.Model.RelatorioOutput;
import com.Projeto1.SFinanceiro.domain.model.Relatorio;
import com.Projeto1.SFinanceiro.domain.model.Transacoes;
import com.Projeto1.SFinanceiro.domain.service.RegistroTransacaoService;
import com.Projeto1.SFinanceiro.domain.service.RelatorioService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("contas/{contaId}/relatorio")
public class RelatorioController {
private RelatorioService relatorioService;
	
	@GetMapping
	public Relatorio listar(@PathVariable Long contaId) {
		
		return relatorioService.numeroTransacoes(contaId);
	}
}
