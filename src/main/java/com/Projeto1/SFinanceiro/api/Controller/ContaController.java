package com.Projeto1.SFinanceiro.api.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.Projeto1.SFinanceiro.api.Assembler.ContaAssembler;
import com.Projeto1.SFinanceiro.api.Model.ContaOutput;
import com.Projeto1.SFinanceiro.api.Model.input.ContaInput;
import com.Projeto1.SFinanceiro.domain.model.Contas;
import com.Projeto1.SFinanceiro.domain.repository.ContasRepository;
import com.Projeto1.SFinanceiro.domain.service.ContasService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/contas")
public class ContaController {
	private ContasRepository contasRepository;
	private ContasService contaService;
	private ContaAssembler contaAssembler;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ContaOutput cadastrar (@RequestBody ContaInput contaInput) {
		
		Contas nConta = contaAssembler.toEntity(contaInput);
		Contas novaConta = contaService.cadastrar(nConta);
		
		return contaAssembler.toModel(novaConta);
	}
	
	@GetMapping
	public List<ContaOutput> listar(){
		return contaAssembler.toCollectionModel(contasRepository.findAll());
	}
	
	@GetMapping("/{contaId}")
	public ResponseEntity<ContaOutput> obter(@PathVariable Long contaId){
		return contasRepository.findById(contaId)
				.map(conta -> ResponseEntity.ok(contaAssembler.toModel(conta)))
				.orElse(ResponseEntity.notFound().build());

	}
}
