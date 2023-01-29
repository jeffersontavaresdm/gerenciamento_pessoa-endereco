package com.api.gerenciamento_pessoaendereco.controller;

import com.api.gerenciamento_pessoaendereco.entity.Endereco;
import com.api.gerenciamento_pessoaendereco.entity.Pessoa;
import com.api.gerenciamento_pessoaendereco.entity.dto.EnderecoPayload;
import com.api.gerenciamento_pessoaendereco.repository.EnderecoRepository;
import com.api.gerenciamento_pessoaendereco.repository.PessoaRepository;
import com.api.gerenciamento_pessoaendereco.service.EnderecoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class EnderecoControllerTest {

  @Mock
  private EnderecoRepository enderecoRepository;

  @Mock
  private PessoaRepository pessoaRepository;

  private EnderecoService enderecoService;

  MockMvc controller;

  @BeforeEach
  public void setUp() {
    enderecoService = new EnderecoService(enderecoRepository, pessoaRepository);
    EnderecoController enderecoController = new EnderecoController(enderecoService);
    controller = MockMvcBuilders.standaloneSetup(enderecoController).build();
  }

  @Test
  public void listar_retornaUmaListaDeEnderecoDTO_sePessoaForEncontrada() throws Exception {
    Pessoa pessoa01 = new Pessoa(1L, "Jonh Snow", LocalDate.of(2011, 1, 1), new ArrayList<>());
    Pessoa pessoa02 = new Pessoa(2L, "Aria Stark", LocalDate.of(2011, 2, 2), new ArrayList<>());
    Endereco endereco01 = new Endereco(1L, "RUA ABC", "11111111", 100, "Westeros", pessoa01, true);
    Endereco endereco02 = new Endereco(2L, "RUA DEF", "22222222", 200, "Westeros", pessoa02, true);
    Endereco endereco04 = new Endereco(4L, "RUA JKL", "44444444", 300, "Westeros", pessoa02, false);

    pessoa01.adicionarEndereco(endereco01);
    pessoa02.adicionarEndereco(endereco02);
    pessoa02.adicionarEndereco(endereco04);

    List<Endereco> enderecos01 = List.of(endereco01);
    List<Endereco> enderecos02 = List.of(endereco02, endereco04);

    Mockito.when(enderecoRepository.findAllByPessoaId(pessoa01.getId())).thenReturn(enderecos01);
    Mockito.when(enderecoRepository.findAllByPessoaId(pessoa02.getId())).thenReturn(enderecos02);

    controller
      .perform(MockMvcRequestBuilders.get("/endereco/listar/1"))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
      .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
      .andExpect(MockMvcResultMatchers.jsonPath("$[0].logradouro", Matchers.is("RUA ABC")));

    controller
      .perform(MockMvcRequestBuilders.get("/endereco/listar/2"))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
      .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(2)))
      .andExpect(MockMvcResultMatchers.jsonPath("$[0].logradouro", Matchers.is("RUA DEF")))
      .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(4)))
      .andExpect(MockMvcResultMatchers.jsonPath("$[1].logradouro", Matchers.is("RUA JKL")));
  }

  @Test
  public void criar_retornarEnderecoDTO_seCriado() throws Exception {
    Pessoa pessoa = criarPessoa();
    EnderecoPayload enderecoPayload = new EnderecoPayload(
      "RUA XYZ",
      "12345678",
      123,
      "Foo"
    );

    Endereco enderecoCriado = enderecoPayload.toEntity(pessoa, false);

    Mockito.when(pessoaRepository.findPessoaById(pessoa.getId())).thenReturn(pessoa);
    Mockito.when(enderecoRepository.save(Mockito.any(Endereco.class))).thenReturn(enderecoCriado);

    controller
      .perform(
        MockMvcRequestBuilders.post("/endereco/criar/1")
          .contentType(MediaType.APPLICATION_JSON)
          .content(converterParaJson(enderecoPayload))
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.jsonPath("$.logradouro", Matchers.is("RUA XYZ")))
      .andExpect(MockMvcResultMatchers.jsonPath("$.numero", Matchers.is(123)));
  }

  @Test
  public void definirEnderecoPrincipal_retornarEnderecoDTO_sePessoaEEnderecoEncontradoEHouverRelacaoEntreEles() throws Exception {
    Pessoa pessoa = criarPessoa();
    Endereco novoEnderecoPrincipal = new Endereco(2L, "RUA XYZ", "12345678", 999, "???", pessoa, false);

    Long pessoaId = pessoa.getId();
    Long enderecoId = novoEnderecoPrincipal.getId();

    List<Endereco> enderecos = List.of(pessoa.getEnderecoPrincipal(), novoEnderecoPrincipal);

    Mockito.when(pessoaRepository.findPessoaById(pessoaId)).thenReturn(pessoa);
    Mockito.when(enderecoRepository.findEnderecoById(enderecoId)).thenReturn(novoEnderecoPrincipal);
    Mockito.when(enderecoRepository.findAllByPessoaId(pessoaId)).thenReturn(enderecos);

    controller.perform(
      MockMvcRequestBuilders
        .patch("/endereco/definir-endereco-principal/{pessoaId}/{enderecoId}", pessoaId, enderecoId)
    ).andExpect(MockMvcResultMatchers.status().isOk());
  }

  private String converterParaJson(final Object object) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    return objectMapper.writeValueAsString(object);
  }

  private Pessoa criarPessoa() {
    Pessoa pessoa = new Pessoa(1L, "Jonh Snow", LocalDate.of(2011, 1, 1), new ArrayList<>());
    Endereco endereco = new Endereco(1L, "Rua ABC", "11111111", 100, "Westeros", pessoa, true);
    pessoa.adicionarEndereco(endereco);

    return pessoa;
  }
}