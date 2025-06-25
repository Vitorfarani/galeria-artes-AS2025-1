package com.galeria.artes.service;

import com.galeria.artes.dto.ArtistaDTO;
import com.galeria.artes.dto.ArtistaDetalhadoDTO;
import com.galeria.artes.model.Artista;
import com.galeria.artes.model.Obra;
import com.galeria.artes.repository.ArtistaRepository;
import com.galeria.artes.repository.ObraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistaService {

    @Autowired
    private ArtistaRepository repository;

    @Autowired
    private ObraRepository obraRepository;

    public Artista salvar(ArtistaDTO dto) {
        Artista artista = new Artista(null, dto.nome(), dto.biografia(), dto.dataNascimento());
        return repository.save(artista);
    }

    public List<Artista> listar() {
        return repository.findAll();
    }

    public Artista buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artista não encontrado com id: " + id));
    }

    public Artista atualizar(Long id, ArtistaDTO dto) {
        Artista artista = buscarPorId(id);
        artista.setNome(dto.nome());
        artista.setBiografia(dto.biografia());
        artista.setDataNascimento(dto.dataNascimento());
        return repository.save(artista);
    }

    public void deletar(Long id) {
        Artista artista = buscarPorId(id);
        repository.delete(artista);
    }

    public List<Artista> buscarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }

    public ArtistaDetalhadoDTO buscarDetalhadoPorId(Long id) {
        Artista artista = buscarPorId(id);
        List<Obra> obras = obraRepository.findByArtistaId(id);
        return new ArtistaDetalhadoDTO(
                artista.getId(),
                artista.getNome(),
                artista.getBiografia(),
                artista.getDataNascimento(),
                obras
        );
    }
}
