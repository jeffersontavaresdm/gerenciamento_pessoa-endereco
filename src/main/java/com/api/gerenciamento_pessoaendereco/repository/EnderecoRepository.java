package com.api.gerenciamento_pessoaendereco.repository;

import com.api.gerenciamento_pessoaendereco.entity.Endereco;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnderecoRepository extends BaseRepository<Endereco> {

  @Query("SELECT e FROM Endereco e WHERE e.id = :id")
  Endereco findEnderecoById(@Param("id") Long id);

  List<Endereco> findAllByPessoaId(Long pessoaId);
}