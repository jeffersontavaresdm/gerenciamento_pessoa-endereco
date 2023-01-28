package com.api.gerenciamento_pessoaendereco.exception;

import com.api.gerenciamento_pessoaendereco.exception.base.NotFoundException;

public class PessoaNaoEncontradaException extends NotFoundException {

  public PessoaNaoEncontradaException() {
    super("pessoa.nao.encontrada");
  }

  public PessoaNaoEncontradaException(String mensagem) {
    super(mensagem);
  }
}