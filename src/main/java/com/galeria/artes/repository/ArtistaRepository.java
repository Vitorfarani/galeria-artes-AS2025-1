package com.galeria.artes.repository;

import com.galeria.artes.model.Artista;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ArtistaRepository extends JpaRepository<Artista, Long> {
    List<Artista> findByNomeContainingIgnoreCase(String nome);



}
