package com.galeria.artes.dto;

public record ObraDetalhadaDTO(
        Long id,
        String titulo,
        String descricao,
        String dataCriacao,
        String nomeArtista,
        String urlImagem
) {}
