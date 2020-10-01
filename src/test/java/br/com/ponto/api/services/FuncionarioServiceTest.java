package br.com.ponto.api.services;

import br.com.ponto.api.entities.Funcionario;
import br.com.ponto.api.repositories.FuncionarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioServiceTest {

	@MockBean
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private FuncionarioService funcionarioService;

	@BeforeEach
	public void setUp() throws Exception {
		BDDMockito.given(funcionarioRepository.save(Mockito.any(Funcionario.class))).willReturn(new Funcionario());
		BDDMockito.given(funcionarioRepository.findById(Mockito.anyLong())).willReturn(Optional.of(new Funcionario()));
		BDDMockito.given(funcionarioRepository.findByEmail(Mockito.anyString())).willReturn(Optional.of(new Funcionario()));
		BDDMockito.given(funcionarioRepository.findByCpf(Mockito.anyString())).willReturn(Optional.of(new Funcionario()));
	}

	@Test
	public void testPersistirFuncionario() {
		Funcionario funcionario = funcionarioService.persistir(new Funcionario());

		assertNotNull(funcionario);
	}

	@Test
	public void testBuscarFuncionarioPorId() {
		Optional<Funcionario> funcionario = funcionarioService.buscarPorId(1L);

		assertTrue(funcionario.isPresent());
	}

	@Test
	public void testBuscarFuncionarioPorEmail() {
		Optional<Funcionario> funcionario = funcionarioService.buscarPorEmail("email@email.com");

		assertTrue(funcionario.isPresent());
	}

	@Test
	public void testBuscarFuncionarioPorCpf() {
		Optional<Funcionario> funcionario = funcionarioService.buscarPorCpf("24291173474");

		assertTrue(funcionario.isPresent());
	}

}
