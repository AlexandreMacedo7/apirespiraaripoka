package com.macedo.apirespiraaripoka.entity.dto;

import java.time.LocalDateTime;

import com.macedo.apirespiraaripoka.util.enums.StatusDenuncia;

public record ConsultaStatusDenunciaDtoResponse(

                Long id,
                LocalDateTime dataDenuncia,
                StatusDenuncia status,
                LocalDateTime ultimaAtualizacao) {

}
