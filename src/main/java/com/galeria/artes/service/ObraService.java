package com.galeria.artes.service;

import com.galeria.artes.dto.ObraBase64DTO;
import com.galeria.artes.model.Artista;
import com.galeria.artes.model.Obra;
import com.galeria.artes.repository.ArtistaRepository;
import com.galeria.artes.repository.ObraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

@Service
public class ObraService {

    @Autowired
    private ObraRepository obraRepository;

    @Autowired
    private ArtistaRepository artistaRepository;

    @Value("${app.imagens.diretorio}")
    private String diretorioImagens;

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
        String caminho = diretorioImagens + nomeArquivo;

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
