package com.macedo.apirespiraaripoka.entity.dto;

import java.time.ZonedDateTime;

import com.macedo.apirespiraaripoka.entity.TipoDenuncia;

public record DenunciaDtoResponse(

        Long id,
        ZonedDateTime dateTime,
        String endereco,
        String coordenadasGeografica,
        TipoDenuncia tipoDenuncia,
        String descricao) {

}
