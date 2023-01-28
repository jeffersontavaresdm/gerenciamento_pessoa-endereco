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
import com.api.gerenciamento_pessoaendereco.utils.Log;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EnderecoService extends BaseService<Endereco> {

  private final Logger log = Log.loggerFor(this);

  private final EnderecoRepository enderecoRepository;
  private final PessoaRepository pessoaRepository;

  public EnderecoService(
    EnderecoRepository enderecoRepository,
    PessoaRepository pessoaRepository
  ) {
    super(enderecoRepository);
    this.enderecoRepository = enderecoRepository;
    this.pessoaRepository = pessoaRepository;
  }

  public List<EnderecoDTO> listar(Long pessoaId) {
    List<EnderecoDTO> enderecos = enderecoRepository
      .findAllByPessoaId(pessoaId)
      .stream()
      .map(Endereco::toDTO)
      .collect(Collectors.toList());

    log.info("Listando " + enderecos.size() + " endereços.");

    return enderecos;
  }

  @Transactional
  public EnderecoDTO criar(Long pessoaId, EnderecoPayload payload) {
    Pessoa pessoa = pessoaRepository.findById(pessoaId).orElse(null);

    if (pessoa == null) {
      log.error("Pessoa com id {} não encontrada.", pessoaId);
      throw new PessoaNaoEncontradaException();
    }

    Endereco endereco = enderecoRepository.save(payload.toEntity(pessoa, false));

    return endereco.toDTO();
  }

  @Transactional
  public EnderecoDTO definirEnderecoPrincipal(Long pessoaId, Long enderecoId) {
    Pessoa pessoa = pessoaRepository.findById(pessoaId).orElse(null);

    if (pessoa == null) {
      String msg = "Pessoa com id " + pessoaId + " não encontrada.";
      log.error(msg);
      throw new PessoaNaoEncontradaException(msg);
    }

    Endereco endereco = findByIdOrNull(enderecoId);

    if (endereco == null) {
      String msg = "Endereço com id " + enderecoId + " não encontrado.";
      log.error(msg);
      throw new EnderecoNaoEncontradoException(msg);
    }

    validarRelacionamentoPessoaEndereco(pessoaId, enderecoId);

    Endereco enderecoAntigo = pessoa
      .getEnderecos()
      .stream()
      .filter(Endereco::getPrincipal)
      .findFirst()
      .orElse(null);

    if (enderecoAntigo != null) {
      enderecoAntigo.setPrincipal(false);
      enderecoRepository.save(enderecoAntigo);
    }

    endereco.setPrincipal(true);
    enderecoRepository.save(endereco);


    pessoaRepository.save(pessoa);

    log.info("Endereço principal da pessoa de id {} atualizado. Novo endereço principal: {}", pessoaId, endereco);

    return endereco.toDTO();
  }

  private void validarRelacionamentoPessoaEndereco(Long pessoaId, Long enderecoId) throws PessoaEnderecoNaoRelacionadoException {
    List<Endereco> enderecos = enderecoRepository.findAllByPessoaId(pessoaId);
    boolean pessoaEnderecoNaoRelacionado = enderecos.stream().noneMatch(e -> Objects.equals(e.getId(), enderecoId));

    if (pessoaEnderecoNaoRelacionado) {
      String mensagem = "Pessoa de id " + pessoaId + " não relacionada com endereço de id " + enderecoId;

      log.error(mensagem);

      throw new PessoaEnderecoNaoRelacionadoException(mensagem);
    }
  }
}