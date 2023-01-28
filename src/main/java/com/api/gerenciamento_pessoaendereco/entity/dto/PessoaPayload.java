package com.api.gerenciamento_pessoaendereco.entity.dto;

import com.api.gerenciamento_pessoaendereco.entity.Pessoa;

import java.time.LocalDate;
import java.util.ArrayList;

public record PessoaPayload(
  String nome,
  LocalDate dataNascimento,
  EnderecoPayload endereco
) {
  public Pessoa toEntity() {
    return new Pessoa(
      null,
      this.nome,
      this.dataNascimento,
      new ArrayList<>()
    );
  }
}