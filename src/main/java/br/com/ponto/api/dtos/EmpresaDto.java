package br.com.ponto.api.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class EmpresaDto {
	
	private Long id;
	private String razaoSocial;
	private String cnpj;

}
