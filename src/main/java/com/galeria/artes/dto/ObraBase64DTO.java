package com.galeria.artes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ObraBase64DTO(
        @NotBlank(message = "Título é obrigatório")
        String titulo,

        @NotBlank(message = "Descrição é obrigatória")
        String descricao,

        @NotNull(message = "ID do artista é obrigatório")
        Long artistaId,

        @NotBlank(message = "Data de criação é obrigatória (formato: YYYY-MM-DD)")
        String dataCriacao,

        @NotBlank(message = "Imagem (base64) é obrigatória")
        String imagemBase64
) {}
