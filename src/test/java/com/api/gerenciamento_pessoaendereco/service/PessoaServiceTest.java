package com.api.gerenciamento_pessoaendereco.service;

import com.api.gerenciamento_pessoaendereco.entity.Endereco;
import com.api.gerenciamento_pessoaendereco.entity.Pessoa;
import com.api.gerenciamento_pessoaendereco.entity.dto.EnderecoPayload;
import com.api.gerenciamento_pessoaendereco.entity.dto.PessoaDTO;
import com.api.gerenciamento_pessoaendereco.entity.dto.PessoaPayload;
import com.api.gerenciamento_pessoaendereco.exception.PessoaJaExisteException;
import com.api.gerenciamento_pessoaendereco.exception.PessoaNaoEncontradaException;
import com.api.gerenciamento_pessoaendereco.repository.EnderecoRepository;
import com.api.gerenciamento_pessoaendereco.repository.PessoaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PessoaServiceTest {

  @Mock
  private PessoaRepository pessoaRepository;

  @Mock
  private EnderecoRepository enderecoRepository;

  @InjectMocks
  private PessoaService pessoaService;

  @Test
  public void buscarPorId_deveRetornarPessoaDTO_quandoEncontrada() {
    Pessoa pessoa = criarPessoa();

    Mockito
      .when(pessoaRepository.findPessoaById(anyLong()))
      .thenReturn(pessoa);

    PessoaDTO pessoaEncontrada = pessoaService.buscarPorId(1L);

    Assertions.assertEquals(pessoa.toDTO(), pessoaEncontrada);
  }

  @Test
  public void buscarPorId_deveLancarExcecao_quandoNaoEncontrada() {
    when(pessoaRepository.findPessoaById(anyLong())).thenReturn(null);

    Assertions.assertThrows(PessoaNaoEncontradaException.class, () -> pessoaService.buscarPorId(1L));
  }

  @Test
  public void buscarPorNomeEDataNascimento_deveRetornarPessoaDTO_quandoEncontrada() {
    String nome = "Jonh Snow";
    LocalDate dataNascimento = LocalDate.of(2011, 1, 1);

    Pessoa pessoa = criarPessoa();

    Mockito
      .when(pessoaRepository.findByNomeAndDataNascimento(Mockito.anyString(), Mockito.any(LocalDate.class)))
      .thenReturn(pessoa);

    PessoaDTO pessoaEncontrada = pessoaService.buscarPorNomeEDataNascimento(nome, dataNascimento);

    Assertions.assertEquals(pessoa.toDTO(), pessoaEncontrada);
  }

  @Test
  public void buscarPorNomeEDataNascimento_deveLancarExcecao_quandoNaoEncontrada() {
    Mockito
      .when(pessoaRepository.findByNomeAndDataNascimento(Mockito.anyString(), Mockito.any(LocalDate.class)))
      .thenReturn(null);

    Assertions.assertThrows(PessoaNaoEncontradaException.class, () -> pessoaService.buscarPorNomeEDataNascimento(
      "Nome Qualquer",
      LocalDate.of(2000, 1, 1))
    );
  }

  @Test
  public void criar_retornarPessoaDTO_quandoNaoHouverPessoaJaCriada() {
    Pessoa pessoa = criarPessoa();

    Mockito.when(pessoaRepository.save(Mockito.any(Pessoa.class))).thenReturn(pessoa);

    PessoaPayload pessoaPayload = criarPessoaPayload(pessoa);
    PessoaDTO pessoaCriada = pessoaService.criar(pessoaPayload);

    Assertions.assertEquals(pessoa.toDTO(), pessoaCriada);

    Mockito
      .verify(pessoaRepository, Mockito.times(1))
      .findByNomeAndDataNascimento(Mockito.anyString(), Mockito.any(LocalDate.class));

    Mockito
      .verify(pessoaRepository, Mockito.times(2))
      .save(Mockito.any(Pessoa.class));
  }

  @Test
  public void criar_deveLancarExcecao_quandoPessoaJaCriada() {
    Pessoa pessoa = criarPessoa();

    Mockito
      .when(pessoaRepository.findByNomeAndDataNascimento(Mockito.anyString(), Mockito.any(LocalDate.class)))
      .thenReturn(pessoa);

    PessoaPayload pessoaPayload = criarPessoaPayload(pessoa);

    Assertions.assertThrows(PessoaJaExisteException.class, () -> pessoaService.criar(pessoaPayload));

    Mockito
      .verify(pessoaRepository, Mockito.times(1))
      .findByNomeAndDataNascimento(Mockito.anyString(), Mockito.any(LocalDate.class));

    Mockito
      .verify(pessoaRepository, Mockito.times(0))
      .save(Mockito.any(Pessoa.class));
  }

  @Test
  public void editar_retornarPessoaDTO_casoPessoaEncontrada() {
    Pessoa pessoa = criarPessoa();
    Endereco endereco = pessoa.getEnderecoPrincipal();

    Long pessoaId = pessoa.getId();

    Mockito.when(pessoaRepository.findPessoaById(pessoaId)).thenReturn(pessoa);
    Mockito.when(pessoaRepository.save(Mockito.any(Pessoa.class))).thenReturn(pessoa);
    Mockito.when(enderecoRepository.save(Mockito.any(Endereco.class))).thenReturn(endereco);

    EnderecoPayload enderecoPayload = criarEnderecoPayload(endereco);
    PessoaPayload pessoaPayload = new PessoaPayload("nome modificado", pessoa.getDataNascimento(), enderecoPayload);

    PessoaDTO pessoaEditada = pessoaService.editar(pessoaId, pessoaPayload);

    Assertions.assertEquals("nome modificado", pessoaEditada.nome());

    Mockito
      .verify(pessoaRepository, Mockito.times(1))
      .findPessoaById(pessoaId);

    Mockito
      .verify(enderecoRepository, Mockito.times(2))
      .save(Mockito.any(Endereco.class));

    Mockito
      .verify(pessoaRepository, Mockito.times(1))
      .save(Mockito.any(Pessoa.class));
  }

  @Test
  public void editar_lancaExcecaoPessoaNaoEncontrada_casoPessoaNaoEncontrada() {
    Long pessoaId = 1L;
    Pessoa pessoa = criarPessoa();
    PessoaPayload pessoaPayload = criarPessoaPayload(pessoa);
    Mockito.when(pessoaRepository.findPessoaById(pessoaId)).thenReturn(null);

    Assertions.assertThrows(PessoaNaoEncontradaException.class, () -> pessoaService.editar(pessoaId, pessoaPayload));

    Mockito
      .verify(pessoaRepository, Mockito.times(1))
      .findPessoaById(pessoaId);

    Mockito
      .verify(pessoaRepository, Mockito.times(0))
      .save(Mockito.any());
  }

  private Pessoa criarPessoa() {
    Pessoa pessoa = new Pessoa(1L, "Jonh Snow", LocalDate.of(2011, 1, 1), new ArrayList<>());
    Endereco endereco = criarEndereco(pessoa);
    pessoa.adicionarEndereco(endereco);

    return pessoa;
  }

  private PessoaPayload criarPessoaPayload(Pessoa pessoa) {
    Endereco enderecoPrincipal = pessoa.getEnderecoPrincipal();
    EnderecoPayload enderecoPayload = criarEnderecoPayload(enderecoPrincipal);

    return new PessoaPayload(pessoa.getNome(), pessoa.getDataNascimento(), enderecoPayload);
  }

  private EnderecoPayload criarEnderecoPayload(Endereco endereco) {
    return new EnderecoPayload(
      endereco.getLogradouro(),
      endereco.getCep(),
      endereco.getNumero(),
      endereco.getCidade()
    );
  }

  private Endereco criarEndereco(Pessoa pessoa) {
    return new Endereco(1L, "RUA ABC", "123", 123123, "CIDADE 123", pessoa, true);
  }
}