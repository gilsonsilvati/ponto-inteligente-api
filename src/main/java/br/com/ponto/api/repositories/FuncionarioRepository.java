package br.com.ponto.api.repositories;

import br.com.ponto.api.entities.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    Optional<Funcionario> findByCpf(String cpf);

    Optional<Funcionario> findByEmail(String email);

    Optional<Funcionario> findByCpfOrEmail(String cpf, String email);

}
