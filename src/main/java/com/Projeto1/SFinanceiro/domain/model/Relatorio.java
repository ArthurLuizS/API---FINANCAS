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
	
	
	private Float valor = 0F;
	
	@Transient
	private Float taxacliente;
	
	@Transient
	private Long transs = 0L;
	
	@Transient
	private OffsetDateTime data_cliente;
	@Transient
	private Float saldoInicial;
	@Transient
	private Float saldoAtual = 0F;
	@Transient
	private Integer MovimentacaoDebito = 0;
	@Transient
	private Integer MovimentacaoCredito = 0;
	@Transient
	private Integer quantidadeContas;
}
