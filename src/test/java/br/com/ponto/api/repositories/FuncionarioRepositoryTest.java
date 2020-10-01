package br.com.ponto.api.repositories;

import br.com.ponto.api.entities.Empresa;
import br.com.ponto.api.entities.Funcionario;
import br.com.ponto.api.enums.Perfil;
import br.com.ponto.api.utils.PasswordUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioRepositoryTest {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private EmpresaRepository empresaRepository;

	private static final String EMAIL = "email@email.com";
	private static final String CPF = "24291173474";

	@BeforeEach
	public void setUp() throws Exception {
		Empresa empresa = empresaRepository.save(obterDadosEmpresa());
		funcionarioRepository.save(obterDadosFuncionario(empresa));
	}

	@AfterEach
	public final void tearDown() {
		empresaRepository.deleteAll();
	}

	@Test
	public void testBuscarFuncionarioPorEmail() {
		Optional<Funcionario> funcionarioOptional = funcionarioRepository.findByEmail(EMAIL);

		assertEquals(EMAIL, funcionarioOptional.get().getEmail());
	}

	@Test
	public void testBuscarFuncionarioPorCpf() {
		Optional<Funcionario> funcionarioOptional = funcionarioRepository.findByCpf(CPF);

		assertEquals(CPF, funcionarioOptional.get().getCpf());
	}

	@Test
	public void testBuscarFuncionarioPorEmailECpf() {
		Optional<Funcionario> funcionarioOptional = funcionarioRepository.findByCpfOrEmail(CPF, EMAIL);

		assertNotNull(funcionarioOptional.get());
	}

	@Test
	public void testBuscarFuncionarioPorEmailOuCpfParaEmailInvalido() {
		Optional<Funcionario> funcionarioOptional = funcionarioRepository.findByCpfOrEmail(CPF, "email@invalido.com");

		assertNotNull(funcionarioOptional.get());
	}

	@Test
	public void testBuscarFuncionarioPorEmailECpfParaCpfInvalido() {
		Optional<Funcionario> funcionarioOptional = this.funcionarioRepository.findByCpfOrEmail("12345678901", EMAIL);

		assertNotNull(funcionarioOptional.get());
	}

	private Funcionario obterDadosFuncionario(Empresa empresa) throws NoSuchAlgorithmException {
		var funcionario = new Funcionario();
		funcionario.setNome("Fulano de Tal");
		funcionario.setPerfil(Perfil.ROLE_USUARIO);
		funcionario.setSenha(PasswordUtils.gerarBCrypt("123456"));
		funcionario.setCpf(CPF);
		funcionario.setEmail(EMAIL);
		funcionario.setEmpresa(empresa);

		return funcionario;
	}

	private Empresa obterDadosEmpresa() {
		var empresa = new Empresa();
		empresa.setRazaoSocial("Empresa de exemplo");
		empresa.setCnpj("51463645000100");

		return empresa;
	}

}
