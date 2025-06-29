package com.galeria.artes.controller;

import com.galeria.artes.dto.ExposicaoDTO;
import com.galeria.artes.model.Exposicao;
import com.galeria.artes.service.ExposicaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exposicoes")
public class ExposicaoController {

    @Autowired
    private ExposicaoService exposicaoService;

    @PostMapping
    public ResponseEntity<Exposicao> cadastrar(@Valid @RequestBody ExposicaoDTO dto) {
        Exposicao criada = exposicaoService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @GetMapping
    public ResponseEntity<List<Exposicao>> listar(@RequestParam(required = false) String nome) {
        return ResponseEntity.ok(
                (nome == null) ? exposicaoService.listar() : exposicaoService.buscarPorNome(nome)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exposicao> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(exposicaoService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        exposicaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
