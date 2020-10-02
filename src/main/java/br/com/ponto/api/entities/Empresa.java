package br.com.ponto.api.entities;

import br.com.ponto.api.entities.base.EntidadeBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "empresa")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Empresa extends EntidadeBase {

    @Column(name = "razao_social", nullable = false)
    private String razaoSocial;

    @Column(nullable = false)
    private String cnpj;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<Funcionario> funcionarios;

}
