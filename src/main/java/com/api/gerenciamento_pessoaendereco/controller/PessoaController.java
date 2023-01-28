package com.api.gerenciamento_pessoaendereco.controller;

import com.api.gerenciamento_pessoaendereco.entity.dto.PessoaDTO;
import com.api.gerenciamento_pessoaendereco.entity.dto.PessoaPayload;
import com.api.gerenciamento_pessoaendereco.service.PessoaService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/pessoa", produces = MediaType.APPLICATION_JSON_VALUE)
public class PessoaController {

  private final PessoaService pessoaService;

  public PessoaController(PessoaService pessoaService) {
    this.pessoaService = pessoaService;
  }

  @GetMapping("/listar")
  public ResponseEntity<List<PessoaDTO>> listar() {
    List<PessoaDTO> pessoas = pessoaService.listar();
    return ResponseEntity.ok(pessoas);
  }

  @GetMapping("/buscar/{pessoaId}")
  public ResponseEntity<PessoaDTO> buscarPorId(@PathVariable("pessoaId") Long id) {
    PessoaDTO pessoa = pessoaService.buscarPorId(id);
    return ResponseEntity.ok(pessoa);
  }

  @GetMapping("/buscar")
  public ResponseEntity<PessoaDTO> buscarPorNomeEDataNascimento(
    @RequestParam String nome,
    @RequestParam("dataNascimento") LocalDate data
  ) {
    PessoaDTO pessoa = pessoaService.buscarPorNomeEDataNascimento(nome, data);
    return ResponseEntity.ok(pessoa);
  }

  @PostMapping("/criar")
  public ResponseEntity<PessoaDTO> criar(@RequestBody PessoaPayload payload) {
    PessoaDTO pessoa = pessoaService.criar(payload);
    return ResponseEntity.ok(pessoa);
  }

  @PutMapping("/editar/{id}")
  public ResponseEntity<PessoaDTO> editar(@PathVariable("id") Long pessoaId, @RequestBody PessoaPayload payload) {
    PessoaDTO pessoa = pessoaService.editar(pessoaId, payload);
    return ResponseEntity.ok(pessoa);
  }
}