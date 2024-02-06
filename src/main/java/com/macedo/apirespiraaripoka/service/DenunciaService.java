package com.macedo.apirespiraaripoka.service;

import org.springframework.stereotype.Service;

import com.macedo.apirespiraaripoka.entity.dto.CriarDenunciaDtoRequest;
import com.macedo.apirespiraaripoka.entity.dto.DenunciaDtoResponse;
import com.macedo.apirespiraaripoka.repository.DenunciaRepository;
import com.macedo.apirespiraaripoka.util.mapper.DenunciaMapper;

import jakarta.transaction.Transactional;

@Service
public class DenunciaService {

    private final DenunciaRepository repository;
    private final DenunciaMapper mapper;

    public DenunciaService(DenunciaRepository repository, DenunciaMapper denunciaMapper) {
        this.repository = repository;
        this.mapper = denunciaMapper;
    }
    @Transactional
    public DenunciaDtoResponse create(CriarDenunciaDtoRequest dtoRequest) {

        var denuncia = mapper.toEntity(dtoRequest);
        repository.save(denuncia);

        return mapper.toDto(denuncia);
    }
}
