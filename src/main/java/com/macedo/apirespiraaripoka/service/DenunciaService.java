package com.macedo.apirespiraaripoka.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.macedo.apirespiraaripoka.entity.Denuncia;
import com.macedo.apirespiraaripoka.entity.dto.AtualizarStatusDenunciaDtoRequest;
import com.macedo.apirespiraaripoka.entity.dto.ConsultaStatusDenunciaDtoResponse;
import com.macedo.apirespiraaripoka.entity.dto.CriarDenunciaDtoRequest;
import com.macedo.apirespiraaripoka.entity.dto.DenunciaDetalhadaDtoResponse;
import com.macedo.apirespiraaripoka.repository.DenunciaRepository;
import com.macedo.apirespiraaripoka.util.enums.StatusDenuncia;
import com.macedo.apirespiraaripoka.util.enums.TipoDenuncia;
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
    public DenunciaDetalhadaDtoResponse create(CriarDenunciaDtoRequest dtoRequest) {

        var denuncia = mapper.toEntity(dtoRequest);
        repository.save(denuncia);

        return mapper.toDto(denuncia);
    }

    public ConsultaStatusDenunciaDtoResponse getDenunciaById(Long id) {
        var denuncia = findById(id);
        return mapper.toDtoConsulta(denuncia);
    }

    public Page<DenunciaDetalhadaDtoResponse> getAllDenuncia(Pageable pageable) {
        Page<Denuncia> denuncias = repository.findAll(pageable);
        return denuncias.map(mapper::toDto);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public DenunciaDetalhadaDtoResponse updateDenuncia(Long id, AtualizarStatusDenunciaDtoRequest dtoRequest) {

        Denuncia denuncia = findById(id);

        denuncia.atualizaStatusDenuncia(dtoRequest.statusDenuncia());

        repository.save(denuncia);

        return mapper.toDto(denuncia);
    }

    public Page<DenunciaDetalhadaDtoResponse> getDenunciasPorPeriodo(LocalDate startDate, LocalDate endDate, Pageable pageable) {

        Page<Denuncia> denuncias = repository.findByDataDenunciaBetween(startDate.atStartOfDay(),
                endDate.atTime(LocalTime.MAX), pageable);

        return denuncias.map(mapper::toDto);
    }

    public Page<DenunciaDetalhadaDtoResponse> getDenunciasPorTipo(TipoDenuncia tipoDenuncia, Pageable pageable) {

        Page<Denuncia> denuncias = repository.findByTipoDenuncia(tipoDenuncia, pageable);

        return denuncias.map(mapper::toDto);
    }

    public Page<DenunciaDetalhadaDtoResponse> getDenunciasPorPeriodoETipo(LocalDate startDate, LocalDate endDate,
                                                                          TipoDenuncia tipoDenuncia, Pageable pageable) {

        Page<Denuncia> denuncias = repository.findByDataDenunciaBetweenAndTipoDenuncia(startDate.atStartOfDay(),
                endDate.atTime(LocalTime.MAX), tipoDenuncia, pageable);

        return denuncias.map(mapper::toDto);
    }

    public Page<DenunciaDetalhadaDtoResponse> getDenunciaStatus(StatusDenuncia status, Pageable pageable) {

        Page<Denuncia> denuncias = repository.findByStatus(status, pageable);

        return denuncias.map(mapper::toDto);
    }

    public long getTotalDenuncias() {
        return repository.count();
    }

    public Map<TipoDenuncia, Long> getTotalDenunciasPorTipo() {
        return repository.findAll().stream()
                .collect(Collectors.groupingBy(Denuncia::getTipoDenuncia, Collectors.counting()));
    }

    public Map<StatusDenuncia, Long> getTotalDenunciasPorStatus() {
        return repository.findAll().stream()
                .collect(Collectors.groupingBy(Denuncia::getStatusDenuncia, Collectors.counting()));
    }

    private Denuncia findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Denuncia não localizada"));
    }
}
