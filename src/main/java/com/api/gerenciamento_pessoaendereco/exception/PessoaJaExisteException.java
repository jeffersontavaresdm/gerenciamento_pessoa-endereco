package com.api.gerenciamento_pessoaendereco.exception;

import com.api.gerenciamento_pessoaendereco.exception.base.BadRequestException;

public class PessoaJaExisteException extends BadRequestException {

  public PessoaJaExisteException() {
    super("pessoa.ja.existe");
  }

  public PessoaJaExisteException(String mensagem) {
    super(mensagem);
  }
}