package com.api.gerenciamento_pessoaendereco.entity.dto;

public record EnderecoDTO(
  Long id,
  String logradouro,
  String cep,
  Integer numero,
  String cidade
) { }
