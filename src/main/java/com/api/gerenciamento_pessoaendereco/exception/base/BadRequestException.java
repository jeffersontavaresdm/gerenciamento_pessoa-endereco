package com.api.gerenciamento_pessoaendereco.exception.base;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends BaseException {

  public BadRequestException() { }

  public BadRequestException(String mensagem) {
    super(mensagem);
  }
}