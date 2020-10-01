package br.com.ponto.api.entities;

import br.com.ponto.api.entities.base.EntidadeBase;
import br.com.ponto.api.enums.Tipo;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lancamento")
@Getter @Setter
@ToString
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
