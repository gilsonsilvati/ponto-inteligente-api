package br.com.ponto.api.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.Optional;

@NoArgsConstructor
@Getter @Setter
@ToString
public class LancamentoDto {
	
	private Optional<Long> id = Optional.empty();

	@NotEmpty(message = "Data não pode ser vazia.")
	private String data;

	private String tipo;
	private String descricao;
	private String localizacao;
	private Long funcionarioId;

}
