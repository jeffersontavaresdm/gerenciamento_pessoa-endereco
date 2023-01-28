package com.api.gerenciamento_pessoaendereco.service;

import com.api.gerenciamento_pessoaendereco.repository.BaseRepository;

import java.util.List;

public abstract class BaseService<T> {

  private final BaseRepository<T> repository;

  public BaseService(BaseRepository<T> repository) {
    this.repository = repository;
  }

  public List<T> findAll() {
    return repository.findAll();
  }

  public T findByIdOrNull(Long id) {
    return repository.findById(id).orElse(null);
  }
}