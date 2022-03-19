package com.Projeto1.SFinanceiro.domain.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
@JsonInclude(Include.NON_NULL)
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
	private Integer movimentacoes = 0;
	
	@Column(name = "valor")
	private Float taxaCliente = 0F;
	
	//@Transient
	//private Float taxacliente = 0F;
	
	
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
