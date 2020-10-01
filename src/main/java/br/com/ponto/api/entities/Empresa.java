package br.com.ponto.api.entities;

import br.com.ponto.api.entities.base.EntidadeBase;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "empresa")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Empresa extends EntidadeBase {

    @Column(name = "razao_social", nullable = false)
    private String razaoSocial;

    @Column(nullable = false)
    private String cnpj;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<Funcionario> funcionarios;

}
