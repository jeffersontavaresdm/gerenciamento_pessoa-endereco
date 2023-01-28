package com.api.gerenciamento_pessoaendereco.repository;

import com.api.gerenciamento_pessoaendereco.entity.Pessoa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface PessoaRepository extends BaseRepository<Pessoa> {

  Pessoa findByNomeAndDataNascimento(String nome, LocalDate dataNascimento);

  @Query("SELECT p FROM Pessoa p WHERE p.id = :id")
  Pessoa findPessoaById(@Param("id") Long id);
}