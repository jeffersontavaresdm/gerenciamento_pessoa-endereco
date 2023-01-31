package com.api.gerenciamento_pessoaendereco.entity.dto;

import com.api.gerenciamento_pessoaendereco.entity.Endereco;
import com.api.gerenciamento_pessoaendereco.entity.Pessoa;
import jakarta.validation.constraints.*;

public record EnderecoPayload(
  String logradouro,

  @Size(min = 8, max = 8)
  String cep,

  @NotNull
  @Min(value = 1, message = "Numero de digitos não pode ser menor que 1")
  @Digits(integer = 10, fraction = 0, message = "Numero de digitos deve ser de 1 a 10")
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