package com.Projeto1.SFinanceiro.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Projeto1.SFinanceiro.domain.exception.NegocioException;
import com.Projeto1.SFinanceiro.domain.model.Cliente;
import com.Projeto1.SFinanceiro.domain.model.Contas;
import com.Projeto1.SFinanceiro.domain.model.Transacoes;
import com.Projeto1.SFinanceiro.domain.repository.ClienteRepository;
import com.Projeto1.SFinanceiro.domain.repository.ContasRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CrudCliente {
	private ClienteRepository clienteRepository;
	/**/private ContasRepository contaRepository;
		private ContasService contasService;
		private RegistroTransacaoService registroTransacaoService;
	/**/
	
	@Transactional
	public Cliente salvar(Cliente cliente/* Contas conta, Transacoes transacao*/) {
		boolean idEmUso = clienteRepository.findByIdentificador(cliente.getIdentificador())
				.stream()
				.anyMatch(clienteExistente -> !clienteExistente.equals(cliente) );
		if (idEmUso) {
			throw new NegocioException("Cliente já cadastrado");
		}
		Cliente clientesalvo = clienteRepository.save(cliente);
		/*contasService.cadastrar(conta, cliente);
		/*registroTransacaoService.registrar(conta, transacao.getTipoMovimentacao(), transacao.getValor());*/
		return clientesalvo;
	}
	@Transactional
	public void excluir (Long clienteId) {
		clienteRepository.deleteById(clienteId);
		
	
	}
	
	public Cliente buscar(Long clienteId) {
		return clienteRepository.findById(clienteId)
				.orElseThrow(() -> new NegocioException("Cliente não encontrado"));
	}
}
