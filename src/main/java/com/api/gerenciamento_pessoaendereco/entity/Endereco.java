package com.api.gerenciamento_pessoaendereco.entity;

import com.api.gerenciamento_pessoaendereco.entity.dto.EnderecoDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
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

  @ToString.Exclude
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Endereco endereco = (Endereco) o;
    return Objects.equals(id, endereco.id);
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }
}