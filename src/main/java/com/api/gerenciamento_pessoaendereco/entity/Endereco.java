package com.api.gerenciamento_pessoaendereco.entity;

import com.api.gerenciamento_pessoaendereco.entity.dto.EnderecoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String logradouro;
  private String cep;
  private Integer numero;
  private String cidade;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pessoa_id")
  private Pessoa pessoa;

  private Boolean principal;

  public EnderecoDTO toDTO() {
    return new EnderecoDTO(
      this.id,
      this.logradouro,
      this.cep,
      this.numero,
      this.cidade
    );
  }
}