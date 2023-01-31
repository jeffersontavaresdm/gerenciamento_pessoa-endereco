package com.api.gerenciamento_pessoaendereco.exception.handler;

import com.api.gerenciamento_pessoaendereco.exception.*;
import com.api.gerenciamento_pessoaendereco.exception.base.BadRequestException;
import com.api.gerenciamento_pessoaendereco.exception.base.NotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class ControllerExceptionAdvice extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
    @Nullable MethodArgumentNotValidException exception,
    @Nullable HttpHeaders headers,
    @Nullable HttpStatusCode status,
    @Nullable WebRequest request
  ) {
    List<String> erros = exception != null
      ? exception
      .getBindingResult()
      .getAllErrors()
      .stream()
      .map(DefaultMessageSourceResolvable::getDefaultMessage)
      .toList()
      : Collections.emptyList();

    HttpStatusCode statusCode = status != null ? status : HttpStatus.BAD_REQUEST;

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(statusCode, erros.toString());

    return new ResponseEntity<>(problemDetail, headers, statusCode);
  }

  @ExceptionHandler(value = {ValidationException.class})
  public ResponseEntity<ProblemDetail> handler(ValidationException exception) {
    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage()));
  }

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