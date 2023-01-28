package com.api.gerenciamento_pessoaendereco.entity.dto;

import java.time.LocalDate;

public record PessoaDTO(
  Long id,
  String nome,
  LocalDate dataNascimento,
  EnderecoDTO endereco
) { }