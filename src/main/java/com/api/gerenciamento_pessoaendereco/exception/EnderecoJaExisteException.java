package com.api.gerenciamento_pessoaendereco.exception;

import com.api.gerenciamento_pessoaendereco.exception.base.BadRequestException;

public class EnderecoJaExisteException extends BadRequestException {

  public EnderecoJaExisteException() {
    super("endereco.ja.existe");
  }
}