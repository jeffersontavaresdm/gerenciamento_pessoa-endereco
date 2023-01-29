package com.api.gerenciamento_pessoaendereco.service;

import com.api.gerenciamento_pessoaendereco.entity.Endereco;
import com.api.gerenciamento_pessoaendereco.entity.Pessoa;
import com.api.gerenciamento_pessoaendereco.entity.dto.EnderecoDTO;
import com.api.gerenciamento_pessoaendereco.entity.dto.EnderecoPayload;
import com.api.gerenciamento_pessoaendereco.exception.EnderecoNaoEncontradoException;
import com.api.gerenciamento_pessoaendereco.exception.PessoaEnderecoNaoRelacionadoException;
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
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class EnderecoServiceTest {

  @Mock
  private EnderecoRepository enderecoRepository;

  @Mock
  private PessoaRepository pessoaRepository;

  @InjectMocks
  private EnderecoService service;

  @Test
  public void listar_retornarListaEnderecos_sePessoaEncontrada() {
    Pessoa pessoa = criarPessoa();

    Mockito.when(pessoaRepository.findPessoaById(pessoa.getId())).thenReturn(pessoa);
    Mockito.when(enderecoRepository.findAllByPessoaId(pessoa.getId())).thenReturn(List.of(pessoa.getEnderecoPrincipal()));

    List<EnderecoDTO> enderecos = service.listar(1L);

    Assertions.assertFalse(enderecos.isEmpty());
    Assertions.assertEquals(1, enderecos.size());
  }

  @Test
  public void listar_lancarExcecao_sePessoaNaoEncontrada() {
    Mockito.when(pessoaRepository.findPessoaById(Mockito.anyLong())).thenReturn(null);

    Assertions.assertThrows(PessoaNaoEncontradaException.class, () -> service.listar(1L));

    Mockito.verify(enderecoRepository, Mockito.times(0)).findAllByPessoaId(Mockito.anyLong());
  }

  @Test
  public void criar_lancarExcecao_sePessoaNaoEncontrada() {
    Pessoa pessoa = criarPessoa();
    Endereco endereco = pessoa.getEnderecoPrincipal();
    EnderecoPayload enderecoPayload = new EnderecoPayload(
      "RUA XYZ",
      endereco.getCep(),
      999,
      endereco.getCidade()
    );

    Mockito
      .when(pessoaRepository.findPessoaById(pessoa.getId()))
      .thenReturn(null);

    Assertions.assertThrows(PessoaNaoEncontradaException.class, () -> service.criar(pessoa.getId(), enderecoPayload));

    Mockito.verify(enderecoRepository, Mockito.times(0)).save(Mockito.any(Endereco.class));
  }

  @Test
  public void definirEnderecoPrincipal_deveAtualizarEnderecoPrincipal()
    throws PessoaNaoEncontradaException,
    EnderecoNaoEncontradoException,
    PessoaEnderecoNaoRelacionadoException {
    Pessoa pessoa = criarPessoa();
    Endereco endereco = new Endereco(2L, "RUA XYZ", "999", 87654321, "CIDADE 999", pessoa, false);

    Long pessoaId = pessoa.getId();
    Long enderecoId = endereco.getId();

    Mockito
      .when(pessoaRepository.findPessoaById(pessoaId))
      .thenReturn(pessoa);

    Mockito
      .when(enderecoRepository.findEnderecoById(enderecoId))
      .thenReturn(endereco);

    Mockito
      .when(enderecoRepository.findAllByPessoaId(pessoaId))
      .thenReturn(List.of(pessoa.getEnderecoPrincipal(), endereco));

    Endereco enderecoRetornado = service.definirEnderecoPrincipal(pessoaId, enderecoId);

    Assertions.assertEquals(endereco, enderecoRetornado);
    Assertions.assertTrue(endereco.getPrincipal());

    Mockito.verify(pessoaRepository).save(pessoa);
    Mockito.verify(enderecoRepository).save(endereco);
  }

  private Pessoa criarPessoa() {
    Pessoa pessoa = new Pessoa(1L, "Jonh Snow", LocalDate.of(2011, 1, 1), new ArrayList<>());
    Endereco endereco = new Endereco(1L, "RUA ABC", "123", 123123, "CIDADE 123", pessoa, true);
    pessoa.adicionarEndereco(endereco);

    return pessoa;
  }
}