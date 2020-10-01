package br.com.ponto.api.services;

import br.com.ponto.api.entities.Empresa;

import java.util.Optional;

public interface EmpresaService {

	Optional<Empresa> buscarPorCnpj(String cnpj);

	Empresa persistir(Empresa empresa);
	
}
