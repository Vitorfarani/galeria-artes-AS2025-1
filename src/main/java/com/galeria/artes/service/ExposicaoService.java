package com.galeria.artes.service;

import com.galeria.artes.dto.ExposicaoDTO;
import com.galeria.artes.model.Exposicao;
import com.galeria.artes.model.Obra;
import com.galeria.artes.repository.ExposicaoRepository;
import com.galeria.artes.repository.ObraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExposicaoService {

    @Autowired
    private ExposicaoRepository exposicaoRepository;

    @Autowired
    private ObraRepository obraRepository;

    public Exposicao salvar(ExposicaoDTO dto) {
        List<Obra> obras = obraRepository.findAllById(dto.obrasIds());

        Exposicao exposicao = new Exposicao(
                null,
                dto.nome(),
                dto.descricao(),
                dto.data(),
                obras
        );

        return exposicaoRepository.save(exposicao);
    }

    public List<Exposicao> listar() {
        return exposicaoRepository.findAll();
    }

    public Exposicao buscarPorId(Long id) {
        return exposicaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exposição não encontrada com id: " + id));
    }

    public void deletar(Long id) {
        Exposicao exposicao = buscarPorId(id);
        exposicaoRepository.delete(exposicao);
    }

    public List<Exposicao> buscarPorNome(String nome) {
        return exposicaoRepository.findByNomeContainingIgnoreCase(nome);
    }
}