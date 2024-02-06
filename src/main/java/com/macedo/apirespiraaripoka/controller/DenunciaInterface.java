package com.macedo.apirespiraaripoka.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.macedo.apirespiraaripoka.entity.dto.CriarDenunciaDtoRequest;
import com.macedo.apirespiraaripoka.entity.dto.DenunciaDtoResponse;

public interface DenunciaInterface {

    ResponseEntity<DenunciaDtoResponse> create(CriarDenunciaDtoRequest dtoRequest, UriComponentsBuilder uBuilder);

    ResponseEntity<DenunciaDtoResponse> getDenunciaById(Long id);

    ResponseEntity<Page<DenunciaDtoResponse>> getAllDenuncia(Pageable pageable);

    ResponseEntity<?> deleteById(Long id);
}
