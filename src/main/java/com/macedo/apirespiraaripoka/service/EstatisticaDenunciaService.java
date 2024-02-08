package com.macedo.apirespiraaripoka.service;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.macedo.apirespiraaripoka.entity.dto.EstatisticaDenunciaDtoResponse;

@Service
public class EstatisticaDenunciaService {

    private final DenunciaService denunciaService;

    public EstatisticaDenunciaService(DenunciaService denunciaService) {
        this.denunciaService = denunciaService;
    }

    public EstatisticaDenunciaDtoResponse calcularEstatisticasDenuncias() {

        EstatisticaDenunciaDtoResponse dtoResponse = new EstatisticaDenunciaDtoResponse(
                denunciaService.getTotalDenuncias(),
                denunciaService.getTotalDenunciasPorTipo().entrySet().stream().collect(Collectors.toMap(
                        e -> e.getKey().name(), Map.Entry::getValue)),
                denunciaService.getTotalDenunciasPorStatus().entrySet().stream()
                        .collect(Collectors.toMap(e -> e.getKey().name(), Map.Entry::getValue)));

        return dtoResponse;
    }
}
