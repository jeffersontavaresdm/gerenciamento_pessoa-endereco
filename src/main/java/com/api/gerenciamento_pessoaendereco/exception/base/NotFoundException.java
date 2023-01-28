package com.api.gerenciamento_pessoaendereco.exception.base;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends BaseException {

  public NotFoundException() { }

  public NotFoundException(String key) {
    super(key);
  }
}