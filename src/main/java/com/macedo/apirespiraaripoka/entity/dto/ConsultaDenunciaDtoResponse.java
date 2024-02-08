package com.macedo.apirespiraaripoka.entity.dto;

import java.time.LocalDateTime;

import com.macedo.apirespiraaripoka.util.enums.StatusDenuncia;

public record ConsultaDenunciaDtoResponse(

                Long id,
                LocalDateTime dateTime,
                StatusDenuncia status,
                LocalDateTime ultimaAtualizacao) {

}
