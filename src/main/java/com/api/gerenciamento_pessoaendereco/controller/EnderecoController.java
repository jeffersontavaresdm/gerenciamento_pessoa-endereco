package com.api.gerenciamento_pessoaendereco.controller;

import com.api.gerenciamento_pessoaendereco.entity.Endereco;
import com.api.gerenciamento_pessoaendereco.entity.dto.EnderecoDTO;
import com.api.gerenciamento_pessoaendereco.entity.dto.EnderecoPayload;
import com.api.gerenciamento_pessoaendereco.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

  private final EnderecoService enderecoService;

  public EnderecoController(EnderecoService enderecoService) {
    this.enderecoService = enderecoService;
  }

  @GetMapping("/listar/{pessoaId}")
  public ResponseEntity<List<EnderecoDTO>> listar(@PathVariable Long pessoaId) {
    List<EnderecoDTO> enderecos = enderecoService.listar(pessoaId);
    return ResponseEntity.ok(enderecos);
  }

  @PostMapping("/criar/{pessoaId}")
  public ResponseEntity<EnderecoDTO> criarEnderecoParaPessoa(
    @PathVariable Long pessoaId,
    @Valid @RequestBody EnderecoPayload enderecoPayload
  ) {
    EnderecoDTO endereco = enderecoService.criar(pessoaId, enderecoPayload);

    return ResponseEntity.status(HttpStatus.CREATED).body(endereco);
  }

  @PatchMapping("/definir-endereco-principal/{pessoaId}/{enderecoId}")
  public ResponseEntity<Object> definirEnderecoPrincipalParaPessoa(
    @PathVariable Long pessoaId,
    @PathVariable Long enderecoId
  ) {
    Endereco endereco = enderecoService.definirEnderecoPrincipal(pessoaId, enderecoId);

    if (endereco == null) {
      String mensagem = "Pessoa de id " + pessoaId + " não está relacionada com Endereço de id " + enderecoId;

      return ResponseEntity
        .status(HttpStatus.EXPECTATION_FAILED)
        .body(ProblemDetail.forStatusAndDetail(HttpStatus.EXPECTATION_FAILED, mensagem));
    }

    return ResponseEntity.ok(endereco.toDTO());
  }
}