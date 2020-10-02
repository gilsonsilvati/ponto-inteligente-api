package br.com.ponto.api.entities;

import br.com.ponto.api.entities.base.EntidadeBase;
import br.com.ponto.api.enums.Perfil;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "funcionario")
@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario extends EntidadeBase {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String cpf;

    @Column(name = "valor_hora")
    private BigDecimal valorHora;

    @Column(name = "qtd_horas_trabalho_dia")
    private Float quantidadeHorasTrabalhadoDia;

    @Column(name = "qtd_horas_almoco")
    private Float quantidadeHorasAlmoco;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Perfil perfil;

    @ManyToOne
    private Empresa empresa;

    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL)
    private List<Lancamento> lancamentos;

    @Transient
    public Optional<BigDecimal> getValorHoraOptional() {
        return Optional.ofNullable(valorHora);
    }

    @Transient
    public Optional<Float> getQuantidadeHorasTrabalhadoDiaOptional() {
        return Optional.ofNullable(quantidadeHorasTrabalhadoDia);
    }

    @Transient
    public Optional<Float> getQuantidadeHorasAlmocoOptional() {
        return Optional.ofNullable(quantidadeHorasAlmoco);
    }

}
