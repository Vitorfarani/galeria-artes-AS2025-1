package com.galeria.artes.dto;

public record ObraBase64DTO(
        String titulo,
        String descricao,
        Long artistaId,
        String dataCriacao,
        String imagemBase64
) {}
