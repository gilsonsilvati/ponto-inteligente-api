package br.com.ponto.api.repositories;

import br.com.ponto.api.entities.Empresa;
import br.com.ponto.api.entities.Funcionario;
import br.com.ponto.api.entities.Lancamento;
import br.com.ponto.api.enums.Perfil;
import br.com.ponto.api.enums.Tipo;
import br.com.ponto.api.utils.PasswordUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class LancamentoRepositoryTest {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	private Long funcionarioId;

	@BeforeEach
	public void setUp() throws Exception {
		Empresa empresa = empresaRepository.save(obterDadosEmpresa());
		
		Funcionario funcionario = funcionarioRepository.save(obterDadosFuncionario(empresa));
		funcionarioId = funcionario.getId();
		
		lancamentoRepository.save(obterDadosLancamentos(funcionario));
		lancamentoRepository.save(obterDadosLancamentos(funcionario));
	}

	@AfterEach
	public void tearDown() throws Exception {
		empresaRepository.deleteAll();
	}

	@Test
	public void testBuscarLancamentosPorFuncionarioId() {
		List<Lancamento> lancamentos = lancamentoRepository.findByFuncionarioId(funcionarioId);
		
		assertEquals(2, lancamentos.size());
	}
	
	@Test
	public void testBuscarLancamentosPorFuncionarioIdPaginado() {
		PageRequest page = PageRequest.of(0, 10);
		Page<Lancamento> lancamentos = lancamentoRepository.findByFuncionarioId(funcionarioId, page);
		
		assertEquals(2, lancamentos.getTotalElements());
	}
	
	private Lancamento obterDadosLancamentos(Funcionario funcionario) {
		var lancameto = new Lancamento();
		lancameto.setData(LocalDateTime.now());
		lancameto.setTipo(Tipo.INICIO_ALMOCO);
		lancameto.setFuncionario(funcionario);
		lancameto.setDescricao("Test Dados Lançamento");
		lancameto.setLocalizacao("Ceilandia");

		return lancameto;
	}

	private Funcionario obterDadosFuncionario(Empresa empresa) throws NoSuchAlgorithmException {
		var funcionario = new Funcionario();
		funcionario.setNome("Fulano de Tal");
		funcionario.setPerfil(Perfil.ROLE_USUARIO);
		funcionario.setSenha(PasswordUtils.gerarBCrypt("123456"));
		funcionario.setCpf("24291173474");
		funcionario.setEmail("email@email.com");
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
