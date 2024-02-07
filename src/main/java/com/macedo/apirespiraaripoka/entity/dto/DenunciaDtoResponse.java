package com.macedo.apirespiraaripoka.entity.dto;

import java.time.LocalDateTime;

import com.macedo.apirespiraaripoka.util.enums.StatusDenuncia;
import com.macedo.apirespiraaripoka.util.enums.TipoDenuncia;

public record DenunciaDtoResponse(

        Long id,
        LocalDateTime dateTime,
        String endereco,
        String coordenadasGeografica,
        TipoDenuncia tipoDenuncia,
        String descricao,
        StatusDenuncia status) {

}
