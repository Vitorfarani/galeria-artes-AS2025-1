package com.galeria.artes.repository;

import com.galeria.artes.model.Exposicao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExposicaoRepository extends JpaRepository<Exposicao, Long> {
    List<Exposicao> findByNomeContainingIgnoreCase(String nome);
}