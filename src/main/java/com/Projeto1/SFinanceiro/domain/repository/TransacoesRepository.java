package com.Projeto1.SFinanceiro.domain.repository;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Projeto1.SFinanceiro.domain.model.Cliente;
import com.Projeto1.SFinanceiro.domain.model.Transacoes;

@Repository
public interface TransacoesRepository extends JpaRepository<Transacoes, Long> {

	List<Transacoes> findByData(OffsetDateTime data);;

}
