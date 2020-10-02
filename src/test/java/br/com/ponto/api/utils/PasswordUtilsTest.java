//package br.com.ponto.api.utils;
//
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class PasswordUtilsTest {
//
//	private static final String SENHA = "123456";
//	private static final Logger log = LoggerFactory.getLogger(PasswordUtilsTest.class);
//
//	private final BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
//
//	@Test
//	public void testSenhaNula() throws Exception {
//		assertNull(PasswordUtils.gerarBCrypt(null));
//	}
//
//	@Test
//	public void testGerarHashSenha() throws Exception {
//		String hash = PasswordUtils.gerarBCrypt(SENHA);
//		log.info(hash);
//
//		assertTrue(bCryptEncoder.matches(SENHA, hash));
//	}
//
//}
