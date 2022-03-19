package com.Projeto1.SFinanceiro.api.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.Projeto1.SFinanceiro.api.Assembler.TransacaoAssembler;
import com.Projeto1.SFinanceiro.api.Model.TransacaoOutput;
import com.Projeto1.SFinanceiro.domain.model.Transacoes;
import com.Projeto1.SFinanceiro.domain.service.RegistroTransacaoService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("contas/{contaId}/transacoes")
public class TransacaoController {
	
	private RegistroTransacaoService registroTransacaoService;
	private TransacaoAssembler transacaoAssembler;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TransacaoOutput cadastrar(@PathVariable Long contaId, @RequestBody Transacoes transacoes) {
		Transacoes transacao = registroTransacaoService.registrar(contaId, transacoes.getTipoMovimentacao(), transacoes.getValor());
		
		return transacaoAssembler.toModel(transacao);
		
	}
}
