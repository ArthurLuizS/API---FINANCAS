package com.Projeto1.SFinanceiro.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
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

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
public class Cliente {
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private String tipo_cliente;
	
	@Column(name = "identificador_cliente")
	private String identificador;
	
	private String telefone;
	
	private OffsetDateTime data_cliente;
	
	@Embedded
	private Endereco endereco;
	
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<Contas> conta = new ArrayList<>();
	
	public Contas cadastrarContas(String numeroConta /*, String tipoMovimentacao, BigDecimal valor*/) {
		Contas contas = new Contas(); 
		/*contas.setTipoMovimentacao(tipoMovimentacao);
		contas.setValor(valor);*/
		contas.setDataConta(OffsetDateTime.now());
		contas.setCliente(this);
		this.getConta().add(contas);
		
		return contas;
		
	}
	
}
