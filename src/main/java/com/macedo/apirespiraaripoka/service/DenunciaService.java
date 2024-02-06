package com.macedo.apirespiraaripoka.service;

import org.springframework.stereotype.Service;

import com.macedo.apirespiraaripoka.entity.dto.CriarDenunciaDtoRequest;
import com.macedo.apirespiraaripoka.entity.dto.DenunciaDtoResponse;
import com.macedo.apirespiraaripoka.repository.DenunciaRepository;
import com.macedo.apirespiraaripoka.util.mapper.DenunciaMapper;

@Service
public class DenunciaService {

    private final DenunciaRepository repository;
    private final DenunciaMapper mapper;

    public DenunciaService(DenunciaRepository repository, DenunciaMapper denunciaMapper) {
        this.repository = repository;
        this.mapper = denunciaMapper;
    }

    public DenunciaDtoResponse create(CriarDenunciaDtoRequest dtoRequest) {

        System.out.println(dtoRequest.coordenadasGeograficas());
        System.out.println(dtoRequest.endereco());
        System.out.println(dtoRequest.tipoDenuncia());
        System.out.println(dtoRequest.descricao());

        var denuncia = mapper.toEntity(dtoRequest);

        System.out.println(denuncia.getTipoDenuncia());
        repository.save(denuncia);

        return mapper.toDto(denuncia);
    }
}
