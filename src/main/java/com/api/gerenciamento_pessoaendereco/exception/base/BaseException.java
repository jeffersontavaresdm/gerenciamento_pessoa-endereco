package com.api.gerenciamento_pessoaendereco.exception.base;

import org.springframework.lang.Nullable;

public abstract class BaseException extends RuntimeException {

  protected String key;

  protected String detailedMessage;

  public BaseException() { }

  public BaseException(String key) {
    super(key);
    this.key = key;
    this.detailedMessage = null;
  }

  public BaseException(String key, String detailedMessage) {
    super(key);
    this.key = key;
    this.detailedMessage = detailedMessage;
  }

  public BaseException(String key, Throwable cause) {
    super(key, cause);
    this.key = key;
    this.detailedMessage = null;
  }

  public BaseException(Throwable cause) {
    super(cause);
    this.key = null;
    this.detailedMessage = null;
  }

  public BaseException(String key, String detailedMessage, @Nullable Throwable cause) {
    super(key, cause);
    this.key = key;
    this.detailedMessage = detailedMessage;
  }
}