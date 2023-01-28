package com.api.gerenciamento_pessoaendereco.controller;

import com.api.gerenciamento_pessoaendereco.entity.Endereco;
import com.api.gerenciamento_pessoaendereco.entity.Pessoa;
import com.api.gerenciamento_pessoaendereco.entity.dto.EnderecoPayload;
import com.api.gerenciamento_pessoaendereco.entity.dto.PessoaPayload;
import com.api.gerenciamento_pessoaendereco.repository.EnderecoRepository;
import com.api.gerenciamento_pessoaendereco.repository.PessoaRepository;
import com.api.gerenciamento_pessoaendereco.service.PessoaService;
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
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class PessoaControllerTest {

  @Mock
  private PessoaRepository pessoaRepository;

  @Mock
  private EnderecoRepository enderecoRepository;

  private MockMvc controller;

  @BeforeEach
  public void setUp() {
    PessoaService pessoaService = new PessoaService(pessoaRepository, enderecoRepository);
    PessoaController pessoaController = new PessoaController(pessoaService);
    controller = MockMvcBuilders.standaloneSetup(pessoaController).build();
  }

  @Test
  public void listar_retornarListaDePessoaDTO_casoTenhaPessoaSalva() throws Exception {
    Pessoa pessoa01 = new Pessoa(1L, "Jonh Snow", LocalDate.of(2011, 1, 1), new ArrayList<>());
    Pessoa pessoa02 = new Pessoa(2L, "Aria Stark", LocalDate.of(2011, 2, 2), new ArrayList<>());
    Pessoa pessoa03 = new Pessoa(3L, "Robert Baratheon", LocalDate.of(2011, 3, 3), new ArrayList<>());
    Endereco endereco01 = new Endereco(1L, "Rua ABC", "11111111", 100, "Westeros", pessoa01, true);
    Endereco endereco02 = new Endereco(2L, "Rua DEF", "22222222", 200, "Westeros", pessoa02, true);
    Endereco endereco03 = new Endereco(3L, "Rua GHI", "33333333", 300, "Westeros", pessoa03, true);

    pessoa01.adicionarEndereco(endereco01);
    pessoa02.adicionarEndereco(endereco02);
    pessoa03.adicionarEndereco(endereco03);

    List<Pessoa> pessoas = Arrays.asList(pessoa01, pessoa02, pessoa03);

    Mockito
      .when(pessoaRepository.findAll())
      .thenReturn(pessoas);

    controller
      .perform(MockMvcRequestBuilders.get("/pessoa/listar"))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
      .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
      .andExpect(MockMvcResultMatchers.jsonPath("$[1].nome", Matchers.is("Aria Stark")))
      .andExpect(MockMvcResultMatchers.jsonPath("$[2].endereco.logradouro", Matchers.is("Rua GHI")));
  }

  @Test
  public void buscarPorId_retornarPessoaDTO_casoEncontrada() throws Exception {
    Pessoa pessoa = criarPessoa();

    Mockito
      .when(pessoaRepository.findPessoaById(1L))
      .thenReturn(pessoa);

    controller
      .perform(MockMvcRequestBuilders.get("/pessoa/buscar/1"))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
      .andExpect(MockMvcResultMatchers.jsonPath("$.nome", Matchers.is("Jonh Snow")))
      .andExpect(MockMvcResultMatchers.jsonPath("$.endereco.logradouro", Matchers.is("Rua ABC")));
  }

  @Test
  public void buscarPorId_lancarExcecao_casoNaoEncontrada() throws Exception {
    Pessoa pessoa = criarPessoa();

    Mockito
      .when(pessoaRepository.findPessoaById(1L))
      .thenReturn(pessoa);

    controller
      .perform(MockMvcRequestBuilders.get("/pessoa/buscar/2"))
      .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void buscarPorNomeEDataNascimento_retornarPessoaDTO_casoEncontrada() throws Exception {
    Pessoa pessoa = criarPessoa();

    Mockito
      .when(pessoaRepository.findByNomeAndDataNascimento(Mockito.anyString(), Mockito.any(LocalDate.class)))
      .thenReturn(pessoa);

    controller
      .perform(MockMvcRequestBuilders.get("/pessoa/buscar?nome=Jonh Snow&dataNascimento=2011-01-01"))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
      .andExpect(MockMvcResultMatchers.jsonPath("$.nome", Matchers.is("Jonh Snow")))
      .andExpect(MockMvcResultMatchers.jsonPath("$.endereco.logradouro", Matchers.is("Rua ABC")));
  }

  @Test
  public void buscarPorNomeEDataNascimento_lancarExcecao_casoNaoEncontrada() throws Exception {
    Pessoa pessoa = criarPessoa();

    Mockito
      .when(pessoaRepository.findByNomeAndDataNascimento(pessoa.getNome(), pessoa.getDataNascimento()))
      .thenReturn(pessoa);

    controller
      .perform(MockMvcRequestBuilders.get("/pessoa/buscar?nome=Outro Nome&dataNascimento=2011-01-01"))
      .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void criar_retornarPessoaDTO_casoPessoaPayloadSejaValido() throws Exception {
    Pessoa pessoa = criarPessoa();
    Endereco endereco = pessoa.getEnderecoPrincipal();

    EnderecoPayload enderecoPayload = new EnderecoPayload(
      endereco.getLogradouro(),
      endereco.getCep(),
      endereco.getNumero(),
      endereco.getCidade()
    );
    PessoaPayload payload = new PessoaPayload("Jonh Snow", LocalDate.of(2011, 1, 1), enderecoPayload);

    pessoa.adicionarEndereco(endereco);

    Mockito
      .when(pessoaRepository.save(Mockito.any(Pessoa.class)))
      .thenReturn(pessoa);

    controller
      .perform(
        MockMvcRequestBuilders.post("/pessoa/criar")
          .contentType(MediaType.APPLICATION_JSON)
          .content(converterParaJson(payload))
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
      .andExpect(MockMvcResultMatchers.jsonPath("$.nome", Matchers.is("Jonh Snow")))
      .andExpect(MockMvcResultMatchers.jsonPath("$.endereco.logradouro", Matchers.is("Rua ABC")))
      .andExpect(MockMvcResultMatchers.jsonPath("$.endereco.numero", Matchers.is(100)));
  }

  @Test
  public void editar_retornarPessoaDTO_casoPessoaPayloadSejaValido() throws Exception {
    Pessoa pessoa = criarPessoa();
    Endereco endereco = pessoa.getEnderecoPrincipal();

    EnderecoPayload enderecoPayload = new EnderecoPayload(
      endereco.getLogradouro(),
      endereco.getCep(),
      endereco.getNumero(),
      endereco.getCidade()
    );
    PessoaPayload payload = new PessoaPayload("Nome modificado", LocalDate.of(2011, 1, 1), enderecoPayload);

    Mockito
      .when(pessoaRepository.findPessoaById(1L))
      .thenReturn(pessoa);

    Mockito
      .when(pessoaRepository.save(Mockito.any(Pessoa.class)))
      .thenReturn(pessoa);

    Mockito
      .when(enderecoRepository.save(Mockito.any(Endereco.class)))
      .thenReturn(endereco);

    controller
      .perform(
        MockMvcRequestBuilders.put("/pessoa/editar/1")
          .contentType(MediaType.APPLICATION_JSON)
          .content(converterParaJson(payload))
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
      .andExpect(MockMvcResultMatchers.jsonPath("$.nome", Matchers.is("Nome modificado")));
  }

  private String converterParaJson(final Object object) {
    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    try {
      return objectMapper.writeValueAsString(object);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private Pessoa criarPessoa() {
    Pessoa pessoa = new Pessoa(1L, "Jonh Snow", LocalDate.of(2011, 1, 1), new ArrayList<>());
    Endereco endereco = new Endereco(1L, "Rua ABC", "11111111", 100, "Westeros", pessoa, true);
    pessoa.adicionarEndereco(endereco);

    return pessoa;
  }
}