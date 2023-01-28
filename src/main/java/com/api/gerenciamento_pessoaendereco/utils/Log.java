package com.api.gerenciamento_pessoaendereco.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {

  private Log() { }

  public static Logger loggerFor(Object clazz) {
    return LoggerFactory.getLogger(clazz.getClass());
  }
}