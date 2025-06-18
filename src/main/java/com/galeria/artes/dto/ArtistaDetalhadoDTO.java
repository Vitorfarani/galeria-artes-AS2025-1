package com.galeria.artes.dto;

import com.galeria.artes.model.Obra;
import java.time.LocalDate;
import java.util.List;

public record ArtistaDetalhadoDTO(
        Long id,
        String nome,
        String biografia,
        LocalDate dataNascimento,
        List<Obra> obras
) {}