package com.Projeto1.SFinanceiro.api.Controller;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.Projeto1.SFinanceiro.api.Assembler.ClienteAssembler;
import com.Projeto1.SFinanceiro.api.Model.ClienteOutput;
import com.Projeto1.SFinanceiro.api.Model.ContaOutput;
import com.Projeto1.SFinanceiro.api.Model.input.ClienteInput;
import com.Projeto1.SFinanceiro.api.Model.input.ContaInput;
import com.Projeto1.SFinanceiro.domain.model.Cliente;
import com.Projeto1.SFinanceiro.domain.model.Contas;
import com.Projeto1.SFinanceiro.domain.model.Transacoes;
import com.Projeto1.SFinanceiro.domain.repository.ClienteRepository;
import com.Projeto1.SFinanceiro.domain.service.CrudCliente;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {
	@Autowired
	private ClienteRepository clienteRepository;
	private CrudCliente crudCliente;
	private ClienteAssembler clienteAssembler;
	
	@GetMapping
	public List<ClienteOutput> listar() {

		return clienteAssembler.toCollectionModel(clienteRepository.findAll());
	} 
	
	@GetMapping("/{clienteId}")
	public ResponseEntity<ClienteOutput> buscar(@PathVariable Long clienteId) {
		return clienteRepository.findById(clienteId)
				.map(cliente -> ResponseEntity.ok(clienteAssembler.toModel(cliente)))
				.orElse(ResponseEntity.notFound().build());
			
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente cadastrar (@RequestBody ClienteInput clienteInput/* Contas conta, Transacoes transacao*/) {
		clienteInput.setData_cliente(OffsetDateTime.now());
		Cliente nCliente = clienteAssembler.toEntity(clienteInput);
		//Cliente novoCliente = crudCliente.salvar(nCliente);
		/*cliente.cadastrarContas(conta.getNumeroConta());*/
		return crudCliente.salvar(nCliente/* conta, transacao*/);
		
		/*public ContaOutput cadastrar (@RequestBody ContaInput contaInput) {
		
		Contas nConta = contaAssembler.toEntity(contaInput);
		Contas novaConta = contaService.cadastrar(nConta);
		
		return contaAssembler.toModel(novaConta);
	}*/
	}
	
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long clienteId, @RequestBody Cliente cliente){
		if(!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		cliente.setId(clienteId);
		cliente = crudCliente.salvar(cliente);
		return ResponseEntity.ok(cliente);
	}
	
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Cliente> excluir (@PathVariable Long clienteId){
		if(!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		crudCliente.excluir(clienteId);
		
		return ResponseEntity.noContent().build();
	}
}
