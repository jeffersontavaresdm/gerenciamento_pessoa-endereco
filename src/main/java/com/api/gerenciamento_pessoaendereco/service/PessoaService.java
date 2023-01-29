package com.api.gerenciamento_pessoaendereco.service;

import com.api.gerenciamento_pessoaendereco.entity.Endereco;
import com.api.gerenciamento_pessoaendereco.entity.Pessoa;
import com.api.gerenciamento_pessoaendereco.entity.dto.PessoaDTO;
import com.api.gerenciamento_pessoaendereco.entity.dto.PessoaPayload;
import com.api.gerenciamento_pessoaendereco.exception.PessoaJaExisteException;
import com.api.gerenciamento_pessoaendereco.exception.PessoaNaoEncontradaException;
import com.api.gerenciamento_pessoaendereco.repository.EnderecoRepository;
import com.api.gerenciamento_pessoaendereco.repository.PessoaRepository;
import com.api.gerenciamento_pessoaendereco.utils.Log;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PessoaService extends BaseService<Pessoa> {

  private final Logger log = Log.loggerFor(this);

  private final PessoaRepository pessoaRepository;
  private final EnderecoRepository enderecoRepository;

  public PessoaService(
    PessoaRepository pessoaRepository,
    EnderecoRepository enderecoRepository
  ) {
    super(pessoaRepository);
    this.pessoaRepository = pessoaRepository;
    this.enderecoRepository = enderecoRepository;
  }

  /**
   * Retorna a lista de todas as pessoas cadastradas.
   *
   * @return Lista de {@link PessoaDTO}
   */
  public List<PessoaDTO> listar() {
    var pessoas = pessoaRepository
      .findAll()
      .stream()
      .map(Pessoa::toDTO)
      .toList();

    log.info("Quantidade de pessoas encontradas: {}", pessoas.size());

    return pessoas;
  }

  /**
   * Cria uma nova pessoa com base nos dados fornecidos no objeto {@link PessoaPayload}.
   *
   * @param pessoaPayload Dados da pessoa a ser criada
   * @return {@link PessoaDTO} da pessoa criada
   * @throws PessoaJaExisteException caso já exista uma pessoa com o mesmo nome e data de nascimento
   */
  @Transactional
  public PessoaDTO criar(PessoaPayload pessoaPayload) {
    log.info("Criando nova pessoa. Pessoa: {}", pessoaPayload);

    var pessoa = pessoaRepository.findByNomeAndDataNascimento(pessoaPayload.nome(), pessoaPayload.dataNascimento());

    if (pessoa != null) {
      log.error("Erro ao criar pessoa. Pessoa com id {} já existe.", pessoa.getId());
      throw new PessoaJaExisteException();
    }

    Pessoa pessoaSalva = pessoaRepository.save(pessoaPayload.toEntity());
    Endereco endereco = pessoaPayload.endereco().toEntity(pessoaSalva, true);

    pessoaSalva.adicionarEndereco(endereco);

    pessoaRepository.save(pessoaSalva);

    log.info("Pessoa salva com sucesso. Pessoa: {}", pessoaSalva);

    return pessoaSalva.toDTO();
  }

  /**
   * Busca uma pessoa pelo seu identificador.
   *
   * @param id Identificador da pessoa
   * @return {@link PessoaDTO} da pessoa encontrada
   * @throws PessoaNaoEncontradaException caso não exista uma pessoa com o id informado
   */
  public PessoaDTO buscarPorId(Long id) {
    Pessoa pessoa = pessoaRepository.findPessoaById(id);

    if (pessoa != null) {
      log.info("Buscando pessoa com id {}", pessoa.getId());
      return pessoa.toDTO();
    } else {
      log.error("Pessoa com id {} não encontrada.", id);
      throw new PessoaNaoEncontradaException();
    }
  }

  /**
   * Busca uma pessoa pelo seu nome e data de nascimento.
   *
   * @param nome           Nome da pessoa
   * @param dataNascimento Data de nascimento da pessoa
   * @return {@link PessoaDTO} da pessoa encontrada
   * @throws PessoaNaoEncontradaException caso não exista uma pessoa com o nome e e data de nascimento informado
   */
  public PessoaDTO buscarPorNomeEDataNascimento(String nome, LocalDate dataNascimento) {
    Pessoa pessoa = pessoaRepository.findByNomeAndDataNascimento(nome, dataNascimento);

    if (pessoa != null) {
      log.info("Buscando pessoa com id {}", pessoa.getId());
      return pessoa.toDTO();
    } else {
      log.error("Pessoa com nome '{}' e data de nascimento '{}' não encontrada.", nome, dataNascimento);
      throw new PessoaNaoEncontradaException();
    }
  }

  /**
   * Edita uma pessoa existente, com base nos dados fornecidos no objeto {@link PessoaPayload}.
   *
   * @param pessoaId Identificador da pessoa a ser editada
   * @param payload  Dados da pessoa a serem editados
   * @return {@link PessoaDTO} da pessoa editada
   * @throws PessoaNaoEncontradaException caso não exista uma pessoa com o id informado
   */
  @Transactional
  public PessoaDTO editar(Long pessoaId, PessoaPayload payload) {
    log.info("Editando pessoa de id {}...", pessoaId);

    var pessoa = pessoaRepository.findPessoaById(pessoaId);

    if (pessoa == null) {
      log.error("Pessoa com id {} não encontrada.", pessoaId);
      throw new PessoaNaoEncontradaException("Pessoa com id " + pessoaId + " não encontrada.");
    }

    Endereco enderecoAntigo = pessoa.getEnderecoPrincipal();
    enderecoAntigo.setPrincipal(false);
    enderecoRepository.save(enderecoAntigo);

    Endereco endereco = enderecoRepository.save(payload.endereco().toEntity(pessoa, true));

    pessoa.setNome(payload.nome());
    pessoa.setDataNascimento(payload.dataNascimento());
    pessoa.adicionarEndereco(endereco);

    Pessoa pessoaEditada = pessoaRepository.save(pessoa);

    return pessoaEditada.toDTO();
  }
}