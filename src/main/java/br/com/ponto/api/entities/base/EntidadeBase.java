package br.com.ponto.api.entities.base;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EntidadeBase {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate criacao;
    private LocalDate atualizacao;

    @PrePersist
    private void prePersist() {
        criacao = LocalDate.now();
        atualizacao = LocalDate.now();
    }

    @PreUpdate
    private void preUpdate() {
        atualizacao = LocalDate.now();
    }

}
