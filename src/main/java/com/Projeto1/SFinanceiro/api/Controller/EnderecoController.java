package com.Projeto1.SFinanceiro.api.Controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Projeto1.SFinanceiro.domain.model.Cliente;
import com.Projeto1.SFinanceiro.domain.model.Endereco;
import com.Projeto1.SFinanceiro.domain.repository.ClienteRepository;
import com.Projeto1.SFinanceiro.domain.service.CrudCliente;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/clientes/en/")
public class EnderecoController {
	
	private ClienteRepository clienteRepository;
	private CrudCliente crudCliente;
	
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long clienteId, @RequestBody Cliente endereco){
		if(!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		Cliente cliente =  crudCliente.buscar(clienteId);
		
		cliente.setId(clienteId);
		cliente.setEndereco(endereco.getEndereco());
		
		cliente = crudCliente.salvar(cliente);
		return ResponseEntity.ok(cliente);
	}

}
