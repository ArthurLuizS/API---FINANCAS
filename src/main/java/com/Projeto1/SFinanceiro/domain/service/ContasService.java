package com.Projeto1.SFinanceiro.domain.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Projeto1.SFinanceiro.domain.exception.NegocioException;
import com.Projeto1.SFinanceiro.domain.model.Cliente;
import com.Projeto1.SFinanceiro.domain.model.Contas;
import com.Projeto1.SFinanceiro.domain.repository.ClienteRepository;
import com.Projeto1.SFinanceiro.domain.repository.ContasRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ContasService {
	private ContasRepository contasRepository;
	private ClienteRepository clienteRepository;
	//private CrudCliente crudCliente;
	
	public Cliente buscar(Long clienteId) {
		return clienteRepository.findById(clienteId)
				.orElseThrow(() -> new NegocioException("Cliente não encontrado"));
	}
	
	@Transactional
	public Contas cadastrar (Contas conta) {
		
		/*String clienteIdent = cliente.getIdentificador();*/
		
		
		Cliente cliente = clienteRepository.findById(conta.getCliente().getId())
				.orElseThrow( () -> new NegocioException("cliente nao encontrado"));
		
		//Cliente cliente =  crudCliente.buscar(conta.getCliente().getId());
		
//		Contas contan = cliente.cadastrarContas(conta.getNumeroConta());
		
		/**/ conta.setDataConta(OffsetDateTime.now());
	
		conta.setCliente(cliente);
		return contasRepository.save(conta);
	}
}
