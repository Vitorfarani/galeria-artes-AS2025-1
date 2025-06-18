package com.galeria.artes.dto;

import java.time.LocalDate;
import java.util.List;

public record ExposicaoDTO(
        String nome,
        String descricao,
        LocalDate data,
        List<Long> obrasIds
) {}