package com.galeria.artes.controller;

import com.galeria.artes.dto.ObraDTO;
import com.galeria.artes.model.Obra;
import com.galeria.artes.service.ObraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/obras")
public class ObraController {

    @Autowired
    private ObraService obraService;

    @PostMapping
    public ResponseEntity<Obra> cadastrar(
            @RequestParam String titulo,
            @RequestParam String descricao,
            @RequestParam Long artistaId,
            @RequestParam String dataCriacao,
            @RequestParam MultipartFile imagem
    ) throws IOException {
        ObraDTO dto = new ObraDTO(
                titulo,
                descricao,
                artistaId,
                LocalDate.parse(dataCriacao),
                imagem
        );

        Obra obraCriada = obraService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(obraCriada);
    }

    @GetMapping
    public ResponseEntity<List<Obra>> listar() {
        return ResponseEntity.ok(obraService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Obra> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(obraService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        obraService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Obra>> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(obraService.buscarPorTitulo(titulo));
    }
}