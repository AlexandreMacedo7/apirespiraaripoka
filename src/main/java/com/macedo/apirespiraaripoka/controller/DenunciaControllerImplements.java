package com.macedo.apirespiraaripoka.controller;

import java.net.URI;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.macedo.apirespiraaripoka.entity.dto.AtualizarStatusDenunciaDtoRequest;
import com.macedo.apirespiraaripoka.entity.dto.ConsultaStatusDenunciaDtoResponse;
import com.macedo.apirespiraaripoka.entity.dto.CriarDenunciaDtoRequest;
import com.macedo.apirespiraaripoka.entity.dto.DenunciaDetalhadaDtoResponse;
import com.macedo.apirespiraaripoka.entity.dto.EstatisticaDenunciaDtoResponse;
import com.macedo.apirespiraaripoka.service.DenunciaService;
import com.macedo.apirespiraaripoka.service.EstatisticaDenunciaService;
import com.macedo.apirespiraaripoka.util.enums.StatusDenuncia;
import com.macedo.apirespiraaripoka.util.enums.TipoDenuncia;

import jakarta.validation.Valid;

@RequestMapping("/v1/denuncia")
@RestController
public class DenunciaControllerImplements implements DenunciaInterface {

    private final DenunciaService service;
    private final EstatisticaDenunciaService estatisticaDenunciaService;

    public DenunciaControllerImplements(DenunciaService service, EstatisticaDenunciaService estatisticaService) {
        this.service = service;
        this.estatisticaDenunciaService = estatisticaService;
    }

    @PostMapping
    @Override
    public ResponseEntity<DenunciaDetalhadaDtoResponse> create(@RequestBody @Valid CriarDenunciaDtoRequest dtoRequest,
                                                               UriComponentsBuilder uriComponentsBuilder) {

        var denuncia = service.create(dtoRequest);

        URI uri = uriComponentsBuilder.path("/v1/denuncia/{id}").buildAndExpand(denuncia.id()).toUri();

        return ResponseEntity.created(uri).body(denuncia);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<ConsultaStatusDenunciaDtoResponse> getDenunciaById(@PathVariable Long id) {
        var denunciaDto = service.getDenunciaById(id);
        return ResponseEntity.ok().body(denunciaDto);
    }

    @GetMapping("/analise")
    @Override
    public ResponseEntity<Page<DenunciaDetalhadaDtoResponse>> getAllDenuncia(Pageable pageable) {
        Page<DenunciaDetalhadaDtoResponse> page = service.getAllDenuncia(pageable);
        return ResponseEntity.ok().body(page);
    }

    @PutMapping("/analise/{id}")
    public ResponseEntity<DenunciaDetalhadaDtoResponse> updateDenuncia(@PathVariable Long id,
                                                                       @RequestBody AtualizarStatusDenunciaDtoRequest dtoRequest) {
        var denuncia = service.updateDenuncia(id, dtoRequest);
        return ResponseEntity.ok(denuncia);
    }

    @GetMapping("/analise/por-periodo")
    public ResponseEntity<Page<DenunciaDetalhadaDtoResponse>> getDenunciasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Pageable pageable) {
        Page<DenunciaDetalhadaDtoResponse> page = service.getDenunciasPorPeriodo(startDate, endDate, pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/analise/por-tipo")
    public ResponseEntity<Page<DenunciaDetalhadaDtoResponse>> getDenunciasPorTipo(
            @RequestParam TipoDenuncia tipoDenuncia,
            Pageable pageable) {
        Page<DenunciaDetalhadaDtoResponse> page = service.getDenunciasPorTipo(tipoDenuncia, pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/analise/por-periodo-tipo")
    public ResponseEntity<Page<DenunciaDetalhadaDtoResponse>> getDenunciasPorPeriodoTipo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam TipoDenuncia tipoDenuncia,
            Pageable pageable) {
        Page<DenunciaDetalhadaDtoResponse> page = service.getDenunciasPorPeriodoETipo(startDate, endDate, tipoDenuncia,
                pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/analise/por-status")
    public ResponseEntity<Page<DenunciaDetalhadaDtoResponse>> getDenunciaPorStatus(@RequestParam StatusDenuncia status,
                                                                                   Pageable pageable) {
        Page<DenunciaDetalhadaDtoResponse> page = service.getDenunciaStatus(status, pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping("analise/estatisticas")
    public ResponseEntity<EstatisticaDenunciaDtoResponse> getEstatisticasDenuncias() {
        EstatisticaDenunciaDtoResponse estatisticas = estatisticaDenunciaService.calcularEstatisticasDenuncias();
        return ResponseEntity.ok(estatisticas);
    }

}
