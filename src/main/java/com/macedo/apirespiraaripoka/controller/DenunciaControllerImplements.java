package com.macedo.apirespiraaripoka.controller;

import java.net.URI;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.macedo.apirespiraaripoka.entity.dto.CriarDenunciaDtoRequest;
import com.macedo.apirespiraaripoka.entity.dto.DenunciaDtoResponse;
import com.macedo.apirespiraaripoka.service.DenunciaService;

import jakarta.validation.Valid;

@RequestMapping("/denuncias")
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

        URI uri = uriComponentsBuilder.path("denuncias/{id}").buildAndExpand(denuncia.id()).toUri();

        return ResponseEntity.created(uri).body(denuncia);
    }

    @Override
    public ResponseEntity<DenunciaDtoResponse> getDenunciaById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDenunciaById'");
    }

    @Override
    public ResponseEntity<Page<DenunciaDtoResponse>> getAllDenuncia(Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllDenuncia'");
    }

    @Override
    public ResponseEntity<?> deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

}
