package br.com.ponto.api.controllers;

import br.com.ponto.api.dtos.FuncionarioDto;
import br.com.ponto.api.entities.Funcionario;
import br.com.ponto.api.response.Response;
import br.com.ponto.api.services.FuncionarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "*")
public class FuncionarioController {

	private static final Logger log = LoggerFactory.getLogger(FuncionarioController.class);

	@Autowired
	private FuncionarioService funcionarioService;
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<FuncionarioDto>> atualizar(@PathVariable("id") Long id,
															  @Valid @RequestBody FuncionarioDto funcionarioDto, BindingResult result) throws NoSuchAlgorithmException {
		log.info("Atualizando funcionário: {}", funcionarioDto.toString());
		Response<FuncionarioDto> response = new Response<FuncionarioDto>();

		Optional<Funcionario> funcionario = funcionarioService.buscarPorId(id);
		
		if (!funcionario.isPresent()) {
			result.addError(new ObjectError("funcionario", "Funcionário não encontrado."));
		}

		if (result.hasErrors()) {
			log.error("Erro validando funcionário: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		atualizarDadosFuncionario(funcionario.get(), funcionarioDto, result);

		funcionarioService.persistir(funcionario.get());
		response.setData(converterFuncionarioDto(funcionario.get()));

		return ResponseEntity.ok(response);
	}

	private void atualizarDadosFuncionario(Funcionario funcionario, FuncionarioDto funcionarioDto, BindingResult result)
			throws NoSuchAlgorithmException {
		funcionario.setNome(funcionarioDto.getNome());

		if (!funcionario.getEmail().equals(funcionarioDto.getEmail())) {
			funcionarioService.buscarPorEmail(funcionarioDto.getEmail())
					.ifPresent(func -> result.addError(new ObjectError("email", "Email já existente.")));
			funcionario.setEmail(funcionarioDto.getEmail());
		}

		funcionario.setQuantidadeHorasAlmoco(null);
		funcionarioDto.getQuantidadeHorasAlmoco()
				.ifPresent(qtdHorasAlmoco -> funcionario.setQuantidadeHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));

		funcionario.setQuantidadeHorasTrabalhadoDia(null);
		funcionarioDto.getQuantidadeHorasTrabalhadoDia()
				.ifPresent(qtdHorasTrabDia -> funcionario.setQuantidadeHorasTrabalhadoDia(Float.valueOf(qtdHorasTrabDia)));

		funcionario.setValorHora(null);
		funcionarioDto.getValorHora().ifPresent(valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));

		if (funcionarioDto.getSenha().isPresent()) {
//			funcionario.setSenha(PasswordUtils.gerarBCrypt(funcionarioDto.getSenha().get()));
			funcionario.setSenha(funcionarioDto.getSenha().get());
		}
	}

	private FuncionarioDto converterFuncionarioDto(Funcionario funcionario) {
		FuncionarioDto funcionarioDto = new FuncionarioDto();
		funcionarioDto.setId(funcionario.getId());
		funcionarioDto.setEmail(funcionario.getEmail());
		funcionarioDto.setNome(funcionario.getNome());
		
		funcionario.getQuantidadeHorasAlmocoOptional().ifPresent(
				qtdHorasAlmoco -> funcionarioDto.setQuantidadeHorasAlmoco(Optional.of(Float.toString(qtdHorasAlmoco))));
		
		funcionario.getQuantidadeHorasTrabalhadoDiaOptional().ifPresent(
				qtdHorasTrabDia -> funcionarioDto.setQuantidadeHorasTrabalhadoDia(Optional.of(Float.toString(qtdHorasTrabDia))));
		
		funcionario.getValorHoraOptional()
				.ifPresent(valorHora -> funcionarioDto.setValorHora(Optional.of(valorHora.toString())));

		return funcionarioDto;
	}

}
