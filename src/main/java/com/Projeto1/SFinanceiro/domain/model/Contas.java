package com.Projeto1.SFinanceiro.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EqualsAndHashCode
public class Contas {
	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Cliente cliente;
	@Column(name = "numero_conta")
	private String numeroConta;
	/* data de criação da conta*/
	@Column(name = "data_conta")
	private OffsetDateTime dataConta;
	
	@OneToMany(mappedBy = "conta", cascade = CascadeType.ALL)
	private List<Transacoes> transacoes = new ArrayList<>();
	
	/*@Column(name = "saldo_inicial")
	private BigDecimal saldoIn; */
	
	private Float saldo;


	public Transacoes efetuarTransacao(String tipoMovimentacao, Float valor, Float avalor) {
		Transacoes transacao = new Transacoes(); 
		transacao.setTipoMovimentacao(tipoMovimentacao);
		transacao.setSaldo_inicial(avalor);
		transacao.setValor(valor);
		transacao.setData(OffsetDateTime.now());
		transacao.setConta(this);
		
		this.getTransacoes().add(transacao);
		
		return transacao;
		
	}
}
