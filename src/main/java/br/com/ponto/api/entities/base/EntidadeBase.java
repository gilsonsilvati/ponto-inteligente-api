package br.com.ponto.api.entities.base;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
@Getter @Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EntidadeBase {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate criacao;

    @Column(nullable = false)
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
