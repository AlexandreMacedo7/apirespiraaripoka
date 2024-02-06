package com.macedo.apirespiraaripoka.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.macedo.apirespiraaripoka.entity.dto.CriarDenunciaDtoRequest;
import com.macedo.apirespiraaripoka.entity.dto.DenunciaDtoResponse;
import com.macedo.apirespiraaripoka.service.DenunciaService;

import jakarta.validation.Valid;

@RequestMapping("/v1/denuncias")
@RestController
public class DenunciaControllerImplements implements DenunciaInterface {

    private final DenunciaService service;

    public DenunciaControllerImplements(DenunciaService service) {
        this.service = service;
    }

    @PostMapping
    @Override
    public ResponseEntity<DenunciaDtoResponse> create(@RequestBody @Valid CriarDenunciaDtoRequest dtoRequest,
            UriComponentsBuilder uriComponentsBuilder) {

        var denuncia = service.create(dtoRequest);

        URI uri = uriComponentsBuilder.path("/v1/denuncias/{id}").buildAndExpand(denuncia.id()).toUri();

        return ResponseEntity.created(uri).body(denuncia);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<DenunciaDtoResponse> getDenunciaById(@PathVariable Long id) {
        var denunciaDto = service.getDenunciaById(id);
        return ResponseEntity.ok().body(denunciaDto);
    }

    @GetMapping("/gerenciar")
    @Override
    public ResponseEntity<Page<DenunciaDtoResponse>> getAllDenuncia(Pageable pageable) {
        Page<DenunciaDtoResponse> page = service.getAllDenuncia(pageable);
        return ResponseEntity.ok().body(page);
    }

    @DeleteMapping("/gerenciar/{id}")
    @Override
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
