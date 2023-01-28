package com.api.gerenciamento_pessoaendereco.exception.handler;

import com.api.gerenciamento_pessoaendereco.exception.*;
import com.api.gerenciamento_pessoaendereco.exception.base.BadRequestException;
import com.api.gerenciamento_pessoaendereco.exception.base.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class PessoaControllerExceptionAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler(
    value = {
      PessoaJaExisteException.class,
      EnderecoJaExisteException.class,
      PessoaEnderecoNaoRelacionadoException.class
    }
  )
  public ResponseEntity<ProblemDetail> handler(BadRequestException exception) {
    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage()));
  }

  @ExceptionHandler(
    value = {
      PessoaNaoEncontradaException.class,
      EnderecoNaoEncontradoException.class
    }
  )
  public ResponseEntity<ProblemDetail> handler(NotFoundException exception) {
    return ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage()));
  }
}