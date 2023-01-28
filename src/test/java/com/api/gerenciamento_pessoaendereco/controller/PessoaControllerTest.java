package com.api.gerenciamento_pessoaendereco.controller;

import com.api.gerenciamento_pessoaendereco.entity.Endereco;
import com.api.gerenciamento_pessoaendereco.entity.Pessoa;
import com.api.gerenciamento_pessoaendereco.repository.EnderecoRepository;
import com.api.gerenciamento_pessoaendereco.repository.PessoaRepository;
import com.api.gerenciamento_pessoaendereco.service.PessoaService;
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
  public void testListar() throws Exception {
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

    Mockito.when(pessoaRepository.findAll()).thenReturn(pessoas);

    controller.perform(MockMvcRequestBuilders.get("/pessoa/listar"))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
      .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
      .andExpect(MockMvcResultMatchers.jsonPath("$[1].nome", Matchers.is("Aria Stark")))
      .andExpect(MockMvcResultMatchers.jsonPath("$[2].endereco.logradouro", Matchers.is("Rua GHI")));
  }
}