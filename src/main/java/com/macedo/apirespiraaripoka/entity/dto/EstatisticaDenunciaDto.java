package com.macedo.apirespiraaripoka.entity.dto;

import java.util.Map;

public record EstatisticaDenunciaDto(
    long totalDenuncias, 
    Map<String, Long> totalPorTipo,
    Map<String, Long> totalPorStatus
    ) {

}
