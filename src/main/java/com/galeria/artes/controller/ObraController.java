package com.galeria.artes.controller;

import com.galeria.artes.dto.ObraBase64DTO;
import com.galeria.artes.model.Obra;
import com.galeria.artes.service.ObraService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/obras")
public class ObraController {

    @Autowired
    private ObraService obraService;


    @PostMapping("/base64")
    public ResponseEntity<Obra> cadastrarViaBase64(@RequestBody ObraBase64DTO dto) throws IOException {
        Obra obraCriada = obraService.salvarViaJson(dto);
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