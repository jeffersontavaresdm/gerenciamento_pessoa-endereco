package com.api.gerenciamento_pessoaendereco.repository;

import com.api.gerenciamento_pessoaendereco.entity.Endereco;

import java.util.List;

public interface EnderecoRepository extends BaseRepository<Endereco> {
  Endereco findByPessoaIdAndCepAndNumero(Long pessoaId, String cep, Integer numero);

  List<Endereco> findAllByPessoaId(Long pessoaId);
}