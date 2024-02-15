package com.macedo.apirespiraaripoka.controller;

import com.macedo.apirespiraaripoka.entity.dto.AtualizarStatusDenunciaDtoRequest;
import com.macedo.apirespiraaripoka.entity.dto.CriarDenunciaDtoRequest;
import com.macedo.apirespiraaripoka.entity.dto.DenunciaDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

public interface DenunciaInterface {

    ResponseEntity<DenunciaDtoResponse> create(CriarDenunciaDtoRequest dtoRequest, UriComponentsBuilder uBuilder);

    ResponseEntity<?> getDenunciaById(Long id);

    ResponseEntity<Page<DenunciaDtoResponse>> getAllDenuncia(Pageable pageable);

    ResponseEntity<?> deleteById(Long id);
    ResponseEntity<DenunciaDtoResponse> updateDenuncia(Long id, AtualizarStatusDenunciaDtoRequest dtoRequest);
}
