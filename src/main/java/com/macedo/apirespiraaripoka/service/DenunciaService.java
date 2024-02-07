package com.macedo.apirespiraaripoka.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.macedo.apirespiraaripoka.entity.Denuncia;
import com.macedo.apirespiraaripoka.entity.dto.AtualizarStatusDenunciaDtoRequest;
import com.macedo.apirespiraaripoka.entity.dto.CriarDenunciaDtoRequest;
import com.macedo.apirespiraaripoka.entity.dto.DenunciaDtoResponse;
import com.macedo.apirespiraaripoka.repository.DenunciaRepository;
import com.macedo.apirespiraaripoka.util.mapper.DenunciaMapper;

import jakarta.persistence.EntityNotFoundException;
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

    public DenunciaDtoResponse getDenunciaById(Long id) {
        var denuncia = findById(id);
        return mapper.toDto(denuncia);
    }

    public Page<DenunciaDtoResponse> getAllDenuncia(Pageable pageable) {
        Page<Denuncia> denuncias = repository.findAll(pageable);
        return denuncias.map(mapper::toDto);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public DenunciaDtoResponse updateDenuncia (Long id, AtualizarStatusDenunciaDtoRequest dtoRequest){
        
        Denuncia denuncia = findById(id);
        
        denuncia.atualizaStatusDenuncia(dtoRequest.statusDenuncia());

        repository.save(denuncia);

        return mapper.toDto(denuncia);
    }

    private Denuncia findById(Long id){
        return repository.findById(id)
        .orElseThrow(()-> new EntityNotFoundException("Denuncia não localizada"));
    }
}
