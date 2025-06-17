package com.galeria.artes.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exposicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private LocalDate data;

    @ManyToMany
    @JoinTable(
            name = "exposicao_obras",
            joinColumns = @JoinColumn(name = "exposicao_id"),
            inverseJoinColumns = @JoinColumn(name = "obra_id")
    )
    private List<Obra> obras;
}
