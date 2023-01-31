package com.api.gerenciamento_pessoaendereco.entity.dto;

import com.api.gerenciamento_pessoaendereco.entity.Endereco;
import com.api.gerenciamento_pessoaendereco.entity.Pessoa;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EnderecoPayload(
  String logradouro,

  @Size(min = 8, max = 8)
  String cep,

  @NotNull
  @Digits(integer = 10, fraction = 0)
  Integer numero,

  @Pattern(regexp = "[a-zA-Z]+", message = "Nome da cidade deve conter somente letras")
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