package com.galeria.artes.service;

import com.galeria.artes.dto.ObraBase64DTO;
import com.galeria.artes.dto.ObraDetalhadaDTO;
import com.galeria.artes.model.Artista;
import com.galeria.artes.model.Obra;
import com.galeria.artes.repository.ArtistaRepository;
import com.galeria.artes.repository.ObraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    public ObraDetalhadaDTO detalhar(Long id) {
        Obra obra = buscarPorId(id);
        return new ObraDetalhadaDTO(
                obra.getId(),
                obra.getTitulo(),
                obra.getDescricao(),
                obra.getDataCriacao().toString(),
                obra.getArtista().getNome(),
                "/imagens/" + Paths.get(obra.getCaminhoImagem()).getFileName().toString()
        );
    }

    public Obra salvarViaJson(ObraBase64DTO dto) throws IOException {
        try {
            Artista artista = artistaRepository.findById(dto.artistaId())
                    .orElseThrow(() -> new RuntimeException("Artista não encontrado com ID: " + dto.artistaId()));

            byte[] imagemBytes = Base64.getDecoder().decode(dto.imagemBase64());
            String nomeArquivo = dto.titulo().replaceAll("\\s+", "_").toLowerCase() + ".jpg";
            String caminho = diretorioImagens + nomeArquivo;

            // Garante que o diretório existe
            Path pathDiretorio = Paths.get(diretorioImagens);
            Files.createDirectories(pathDiretorio);

            // Log para debug
            System.out.println("Salvando imagem em: " + caminho);

            // Salva a imagem no disco
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
        } catch (Exception e) {
            e.printStackTrace(); // ou use um logger se preferir
            throw new RuntimeException("Erro ao salvar obra: " + e.getMessage(), e);
        }
    }
}
