package com.api.gerenciamento_pessoaendereco.exception;

import com.api.gerenciamento_pessoaendereco.exception.base.NotFoundException;

public class EnderecoNaoEncontradoException extends NotFoundException {

  public EnderecoNaoEncontradoException() {
    super("endereco.nao.encontrado");
  }

  public EnderecoNaoEncontradoException(String mensagem) {
    super(mensagem);
  }
}