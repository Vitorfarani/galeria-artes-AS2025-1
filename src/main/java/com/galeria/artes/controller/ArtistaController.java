package com.galeria.artes.controller;

import com.galeria.artes.dto.ArtistaDTO;
import com.galeria.artes.dto.ArtistaDetalhadoDTO;
import com.galeria.artes.model.Artista;
import com.galeria.artes.service.ArtistaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/artistas")
public class ArtistaController {

    @Autowired
    private ArtistaService service;

    @PostMapping
    public ResponseEntity<Artista> cadastrar(@Valid @RequestBody ArtistaDTO dto) {
        Artista criado = service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping
    public List<Artista> listar(@RequestParam(required = false) String nome) {
        return (nome == null) ? service.listar() : service.buscarPorNome(nome);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistaDetalhadoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarDetalhadoPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artista> atualizar(@PathVariable Long id, @Valid @RequestBody ArtistaDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
