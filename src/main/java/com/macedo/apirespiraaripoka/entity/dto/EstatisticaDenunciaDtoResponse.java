package com.macedo.apirespiraaripoka.entity.dto;

import java.util.Map;

public record EstatisticaDenunciaDtoResponse(
    long totalDenuncias, 
    Map<String, Long> totalPorTipo,
    Map<String, Long> totalPorStatus
    ) {
}
