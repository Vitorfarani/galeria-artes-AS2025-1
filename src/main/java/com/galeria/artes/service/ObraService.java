package com.galeria.artes.service;

import com.galeria.artes.dto.ObraDTO;
import com.galeria.artes.model.Artista;
import com.galeria.artes.model.Obra;
import com.galeria.artes.repository.ArtistaRepository;
import com.galeria.artes.repository.ObraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Base64;
import com.galeria.artes.dto.ObraBase64DTO;


import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ObraService {

    @Autowired
    private ObraRepository obraRepository;

    @Autowired
    private ArtistaRepository artistaRepository;

    private final String DIRETORIO_IMAGENS = "src/main/resources/static/imagens/";

    public Obra salvar(ObraDTO dto) throws IOException {
        Artista artista = artistaRepository.findById(dto.artistaId())
                .orElseThrow(() -> new RuntimeException("Artista não encontrado"));

        String caminhoImagem = salvarImagem(dto.imagem());

        Obra obra = new Obra(
                null,
                dto.titulo(),
                dto.descricao(),
                dto.dataCriacao(),
                caminhoImagem,
                artista
        );

        return obraRepository.save(obra);
    }

    private String salvarImagem(MultipartFile imagem) throws IOException {
        if (imagem == null || imagem.isEmpty()) return null;

        File diretorio = new File(DIRETORIO_IMAGENS);
        if (!diretorio.exists()) diretorio.mkdirs();

        String caminho = DIRETORIO_IMAGENS + imagem.getOriginalFilename();
        imagem.transferTo(new File(caminho));
        return caminho;
    }

    public Obra buscarPorId(Long id) {
        return obraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Obra não encontrada com id: " + id));
    }

    public List<Obra> listar() {
        return obraRepository.findAll();
    }

    public void deletar(Long id) {
        Obra obra = buscarPorId(id);
        obraRepository.delete(obra);
    }

    public List<Obra> buscarPorTitulo(String titulo) {
        return obraRepository.findByTituloContainingIgnoreCase(titulo);
    }

    public Obra salvarViaJson(ObraBase64DTO dto) throws IOException {
        Artista artista = artistaRepository.findById(dto.artistaId())
                .orElseThrow(() -> new RuntimeException("Artista não encontrado"));

        byte[] imagemBytes = Base64.getDecoder().decode(dto.imagemBase64());
        String nomeArquivo = dto.titulo().replaceAll("\\s+", "_").toLowerCase() + ".jpg";
        String caminho = DIRETORIO_IMAGENS + nomeArquivo;

        Files.write(Paths.get(caminho), imagemBytes);

        Obra obra = new Obra(
                null,
                dto.titulo(),
                dto.descricao(),
                LocalDate.parse(dto.dataCriacao()),
                caminho,
                artista
        );

        return obraRepository.save(obra);
    }

}
