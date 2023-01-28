package com.api.gerenciamento_pessoaendereco.controller;

import com.api.gerenciamento_pessoaendereco.entity.Endereco;
import com.api.gerenciamento_pessoaendereco.entity.Pessoa;
import com.api.gerenciamento_pessoaendereco.repository.EnderecoRepository;
import com.api.gerenciamento_pessoaendereco.repository.PessoaRepository;
import com.api.gerenciamento_pessoaendereco.service.EnderecoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;

@ExtendWith(SpringExtension.class)
public class EnderecoControllerTest {

  @Mock
  private EnderecoRepository enderecoRepository;

  @Mock
  private PessoaRepository pessoaRepository;

  MockMvc controller;

  @BeforeEach
  public void setUp() {
    EnderecoService enderecoService = new EnderecoService(enderecoRepository, pessoaRepository);
    EnderecoController enderecoController = new EnderecoController(enderecoService);
    controller = MockMvcBuilders.standaloneSetup(enderecoController).build();
  }

  @Test
  public void listar_retornaUmaListaDeEnderecoDTO_sePessoaForEncontrada() throws Exception {
    Pessoa pessoa = criarPessoa();

    // continua...

    controller
      .perform(MockMvcRequestBuilders.get("/endereco/listar/1"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.hasSize(1)));
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