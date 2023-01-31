package com.api.gerenciamento_pessoaendereco.entity.dto;

import com.api.gerenciamento_pessoaendereco.entity.Pessoa;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.ArrayList;

public record PessoaPayload(
  @NotNull
  String nome,

  @NotNull
  @Past(message = "Data de nascimento deve ser uma data passada")
  LocalDate dataNascimento,
  @Valid EnderecoPayload endereco
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