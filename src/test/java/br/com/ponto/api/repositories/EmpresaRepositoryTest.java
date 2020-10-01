package br.com.ponto.api.repositories;

import br.com.ponto.api.entities.Empresa;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class EmpresaRepositoryTest {
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	private static final String CNPJ = "51463645000100";

	@BeforeEach
	public void setUp() throws Exception {
		var empresa = new Empresa();
		empresa.setRazaoSocial("Empresa de exemplo");
		empresa.setCnpj(CNPJ);

		empresaRepository.save(empresa);
	}
	
	@AfterEach
    public final void tearDown() { 
		empresaRepository.deleteAll();
	}

	@Test
	public void testBuscarPorCnpj() {
		Optional<Empresa> empresaOptional = empresaRepository.findByCnpj(CNPJ);

		assertEquals(CNPJ, empresaOptional.get().getCnpj());
	}

}
