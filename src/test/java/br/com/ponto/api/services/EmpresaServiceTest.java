package br.com.ponto.api.services;

import br.com.ponto.api.entities.Empresa;
import br.com.ponto.api.repositories.EmpresaRepository;
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
public class EmpresaServiceTest {

	@MockBean
	private EmpresaRepository empresaRepository;

	@Autowired
	private EmpresaService empresaService;

	private static final String CNPJ = "51463645000100";

	@BeforeEach
	public void setUp() throws Exception {
		BDDMockito.given(empresaRepository.findByCnpj(Mockito.anyString()))
				.willReturn(Optional.of(new Empresa()));


		BDDMockito.given(empresaRepository.save(Mockito.any(Empresa.class)))
				.willReturn(new Empresa());
	}

	@Test
	public void testBuscarEmpresaPorCnpj() {
		Optional<Empresa> empresaOptional = empresaService.buscarPorCnpj(CNPJ);

		assertTrue(empresaOptional.isPresent());
	}
	
	@Test
	public void testPersistirEmpresa() {
		var empresa = empresaService.persistir(new Empresa());

		assertNotNull(empresa);
	}

}
