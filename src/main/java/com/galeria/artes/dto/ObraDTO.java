package com.galeria.artes.dto;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public record ObraDTO(
        String titulo,
        String descricao,
        Long artistaId,
        LocalDate dataCriacao,
        MultipartFile imagem
) {}
