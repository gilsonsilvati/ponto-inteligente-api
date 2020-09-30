package br.com.ponto.api.entities;

import br.com.ponto.api.entities.base.EntidadeBase;
import br.com.ponto.api.enums.Tipo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lancamento")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lancamento extends EntidadeBase {

    @Column(nullable = false)
    private LocalDateTime data;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private String localizacao;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @ManyToOne
    private Funcionario funcionario;

}
