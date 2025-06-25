package com.galeria.artes.controller;

import com.galeria.artes.dto.ObraBase64DTO;
import com.galeria.artes.dto.ObraDetalhadaDTO;
import com.galeria.artes.model.Obra;
import com.galeria.artes.service.ObraService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/obras")
public class ObraController {

    @Autowired
    private ObraService obraService;

    @PostMapping("/base64")
    public ResponseEntity<Obra> cadastrarViaBase64(@Valid @RequestBody ObraBase64DTO dto) throws IOException {
        Obra obraCriada = obraService.salvarViaJson(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(obraCriada);
    }

    @GetMapping
    public ResponseEntity<List<Obra>> listar(@RequestParam(required = false) String titulo) {
        return ResponseEntity.ok(
                (titulo == null) ? obraService.listar() : obraService.buscarPorTitulo(titulo)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Obra> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(obraService.buscarPorId(id));
    }

    @GetMapping("/{id}/detalhes")
    public ResponseEntity<ObraDetalhadaDTO> detalhar(@PathVariable Long id) {
        return ResponseEntity.ok(obraService.detalhar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        obraService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/galeria")
    public ResponseEntity<List<ObraDetalhadaDTO>> listarGaleria() {
        List<ObraDetalhadaDTO> galeria = obraService.listar().stream()
                .map(obra -> new ObraDetalhadaDTO(
                        obra.getId(),
                        obra.getTitulo(),
                        obra.getDescricao(),
                        obra.getDataCriacao().toString(),
                        obra.getArtista().getNome(),
                        "/imagens/" + Paths.get(obra.getCaminhoImagem()).getFileName().toString()
                ))
                .toList();

        return ResponseEntity.ok(galeria);
    }
}
