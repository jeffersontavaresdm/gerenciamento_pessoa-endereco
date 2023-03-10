package com.api.gerenciamento_pessoaendereco.entity;

import com.api.gerenciamento_pessoaendereco.entity.dto.PessoaDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nome;

  @Column(name = "data_nascimento")
  private LocalDate dataNascimento;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "pessoa_id")
  private List<Endereco> enderecos = new ArrayList<>();

  public void adicionarEndereco(Endereco endereco) {
    if (endereco != null) {
      this.enderecos.add(endereco);
    }
  }

  public Endereco getEnderecoPrincipal() {
    return this.enderecos
      .stream()
      .filter(Endereco::getPrincipal)
      .findFirst()
      .orElse(null);
  }

  public PessoaDTO toDTO() {
    return new PessoaDTO(
      this.id,
      this.nome,
      this.dataNascimento,
      Optional.ofNullable(getEnderecoPrincipal()).map(Endereco::toDTO).orElse(null)
    );
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Pessoa pessoa = (Pessoa) o;
    return Objects.equals(id, pessoa.id);
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "(" +
      "id = " + id + ", " +
      "nome = " + nome + ", " +
      "dataNascimento = " + dataNascimento +
      getEnderecoPrincipal() + ")";
  }
}