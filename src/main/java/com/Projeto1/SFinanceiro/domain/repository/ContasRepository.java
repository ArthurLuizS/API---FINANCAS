package com.Projeto1.SFinanceiro.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Projeto1.SFinanceiro.domain.model.Contas;

@Repository
public interface ContasRepository extends JpaRepository<Contas, Long> {

}
