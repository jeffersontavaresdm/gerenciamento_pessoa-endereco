package com.api.gerenciamento_pessoaendereco.entity.dto;

import com.api.gerenciamento_pessoaendereco.entity.Endereco;
import com.api.gerenciamento_pessoaendereco.entity.Pessoa;

public record EnderecoPayload(
  String logradouro,
  String cep,
  Integer numero,
  String cidade
) {
  public Endereco toEntity(Pessoa pessoa, boolean principal) {
    return new Endereco(
      null,
      this.logradouro,
      this.cep,
      this.numero,
      this.cidade,
      pessoa,
      principal
    );
  }
}