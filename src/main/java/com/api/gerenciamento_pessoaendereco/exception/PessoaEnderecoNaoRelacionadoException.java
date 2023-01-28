package com.api.gerenciamento_pessoaendereco.exception;

import com.api.gerenciamento_pessoaendereco.exception.base.BadRequestException;

public class PessoaEnderecoNaoRelacionadoException extends BadRequestException {

  public PessoaEnderecoNaoRelacionadoException() { }

  public PessoaEnderecoNaoRelacionadoException(String mensagem) {
    super(mensagem);
  }
}