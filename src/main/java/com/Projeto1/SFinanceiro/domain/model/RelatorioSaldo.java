package com.Projeto1.SFinanceiro.domain.model;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelatorioSaldo {
	private String cliente;
	private Contas contas;
	private OffsetDateTime data;
	/*Cliente: X - Cliente desde: DD/MM/YYYY – Saldo em DD/MM/YYYY: 0.000,00
Cliente: Y - Cliente desde: DD/MM/YYYY - Saldo em DD/MM/YYYY: 000,00
Cliente: Z - Cliente desde: DD/MM/YYYY - Saldo em DD/MM/YYYY: 00,00*/
}
