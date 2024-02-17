package com.macedo.demo.domain.builders;

import com.macedo.apirespiraaripoka.entity.dto.ConsultaStatusDenunciaDtoResponse;
import com.macedo.apirespiraaripoka.entity.dto.CriarDenunciaDtoRequest;
import com.macedo.apirespiraaripoka.entity.dto.DenunciaDetalhadaDtoResponse;
import com.macedo.apirespiraaripoka.util.enums.StatusDenuncia;
import com.macedo.apirespiraaripoka.util.enums.TipoDenuncia;

import java.time.LocalDate;
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

    public static ConsultaStatusDenunciaDtoResponse consultaStatusDenunciaDtoResponse(){
        return new ConsultaStatusDenunciaDtoResponse(
                1L,
                LocalDateTime.of(2024,1,1,06,30),
                StatusDenuncia.RECEBIDA,
                null
        );
    }
}
