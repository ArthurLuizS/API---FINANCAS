package com.Projeto1.SFinanceiro.domain.model;

import java.time.OffsetDateTime;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class RelatorioPeriodo  extends Relatorio{
	private Date dataInicio;
	private Date dataFim;
}
