package br.com.ponto.api.controller;

import br.com.ponto.api.dtos.LancamentoDto;
import br.com.ponto.api.entities.Funcionario;
import br.com.ponto.api.entities.Lancamento;
import br.com.ponto.api.enums.Tipo;
import br.com.ponto.api.services.FuncionarioService;
import br.com.ponto.api.services.LancamentoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LancamentoControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private LancamentoService lancamentoService;
	
	@MockBean
	private FuncionarioService funcionarioService;
	
	private static final String URL_BASE = "/api/lancamentos/";
	private static final Long ID_FUNCIONARIO = 1L;
	private static final Long ID_LANCAMENTO = 1L;
	private static final String TIPO = Tipo.INICIO_TRABALHO.name();
	private static final LocalDateTime DATA = LocalDateTime.now();
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@Test
	public void testCadastrarLancamento() throws Exception {
		Lancamento lancamento = obterDadosLancamento();
		BDDMockito.given(funcionarioService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(new Funcionario()));
		BDDMockito.given(lancamentoService.persistir(Mockito.any(Lancamento.class))).willReturn(lancamento);

		mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
				.content(obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID_LANCAMENTO))
				.andExpect(jsonPath("$.data.tipo").value(TIPO))
				.andExpect(jsonPath("$.data.data").value(DATA.format(formatter)))
				.andExpect(jsonPath("$.data.funcionarioId").value(ID_FUNCIONARIO))
				.andExpect(jsonPath("$.errors").isEmpty());
	}
	
	@Test
	public void testCadastrarLancamentoFuncionarioIdInvalido() throws Exception {
		BDDMockito.given(funcionarioService.buscarPorId(Mockito.anyLong())).willReturn(Optional.empty());

		mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
				.content(obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value("Funcionário não encontrado. ID inexistente."))
				.andExpect(jsonPath("$.data").isEmpty());
	}
	
	@Test
	public void testRemoverLancamento() throws Exception {
		BDDMockito.given(lancamentoService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(new Lancamento()));

		mvc.perform(MockMvcRequestBuilders.delete(URL_BASE + ID_LANCAMENTO)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	private String obterJsonRequisicaoPost() throws JsonProcessingException {
		LancamentoDto lancamentoDto = new LancamentoDto();
		lancamentoDto.setId(null);
		lancamentoDto.setData(DATA.format(formatter));
		lancamentoDto.setTipo(TIPO);
		lancamentoDto.setFuncionarioId(ID_FUNCIONARIO);
		ObjectMapper mapper = new ObjectMapper();
		
		return mapper.writeValueAsString(lancamentoDto);
	}

	private Lancamento obterDadosLancamento() {
		Lancamento lancamento = new Lancamento();
		lancamento.setId(ID_LANCAMENTO);
		lancamento.setData(DATA);
		lancamento.setTipo(Tipo.valueOf(TIPO));
		lancamento.setFuncionario(new Funcionario());
		lancamento.getFuncionario().setId(ID_FUNCIONARIO);
		
		return lancamento;
	}

}
