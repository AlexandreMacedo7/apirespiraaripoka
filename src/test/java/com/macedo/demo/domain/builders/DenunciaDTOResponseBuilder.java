package com.macedo.demo.domain.builders;

import com.macedo.apirespiraaripoka.entity.dto.CriarDenunciaDtoRequest;
import com.macedo.apirespiraaripoka.entity.dto.DenunciaDetalhadaDtoResponse;
import com.macedo.apirespiraaripoka.util.enums.StatusDenuncia;
import com.macedo.apirespiraaripoka.util.enums.TipoDenuncia;

import java.time.LocalDateTime;

public class DenunciaDTOResponseBuilder {

    public static DenunciaDetalhadaDtoResponse denunciaDetalhadaDtoResponse(){
        return new DenunciaDetalhadaDtoResponse(
                1L,
                LocalDateTime.now(),
                "Endereço válido",
                "Coordenadas",
                TipoDenuncia.DESMATAMENTO_URBANO,
                "Descrição válida",
                StatusDenuncia.RECEBIDA
        );
    }
}
