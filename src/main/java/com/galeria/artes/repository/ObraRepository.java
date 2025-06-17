package com.galeria.artes.repository;

import com.galeria.artes.model.Obra;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ObraRepository extends JpaRepository<Obra, Long> {
    List<Obra> findByArtistaId(Long artistaId);
    List<Obra> findByTituloContainingIgnoreCase(String titulo);

}
