package com.Projeto1.SFinanceiro.domain.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Relatorio {
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Transient
	private String cliente;
	
	private Long cliente_Id;
	
	@Transient
	private String endereco;
	private Integer movimentacoes;
	
	
	private Float valor;
	
	@Transient
	private OffsetDateTime data_cliente;
	@Transient
	private Float saldoInicial;
	@Transient
	private Float saldoAtual;
	@Transient
	private Integer MovimentacaoDebito;
	@Transient
	private Integer MovimentacaoCredito;
}
