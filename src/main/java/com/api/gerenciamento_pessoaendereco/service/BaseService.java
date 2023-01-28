package com.api.gerenciamento_pessoaendereco.service;

import com.api.gerenciamento_pessoaendereco.repository.BaseRepository;

public abstract class BaseService<T> {

  private final BaseRepository<T> repository;

  public BaseService(BaseRepository<T> repository) {
    this.repository = repository;
  }

  public T findByIdOrNull(Long id) {
    return repository.findById(id).orElse(null);
  }
}