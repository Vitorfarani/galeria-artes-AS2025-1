package com.galeria.artes.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record ExposicaoDTO(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotBlank(message = "Descrição é obrigatória")
        String descricao,

        @NotNull(message = "Data da exposição é obrigatória")
        @FutureOrPresent(message = "A data da exposição deve ser hoje ou no futuro")
        LocalDate data,

        @NotNull(message = "Lista de obras não pode ser nula")
        List<@NotNull(message = "ID da obra não pode ser nulo") Long> obrasIds
) {}
