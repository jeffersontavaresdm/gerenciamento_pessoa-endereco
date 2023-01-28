package com.api.gerenciamento_pessoaendereco.repository;

import com.api.gerenciamento_pessoaendereco.entity.Pessoa;

import java.time.LocalDate;

public interface PessoaRepository extends BaseRepository<Pessoa> {

  Pessoa findByNomeAndDataNascimento(String nome, LocalDate dataNascimento);
}