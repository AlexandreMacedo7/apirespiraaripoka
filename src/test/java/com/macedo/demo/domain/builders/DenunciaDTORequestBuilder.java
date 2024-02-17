package com.macedo.demo.domain.builders;

import com.macedo.apirespiraaripoka.entity.dto.CriarDenunciaDtoRequest;
import com.macedo.apirespiraaripoka.util.enums.TipoDenuncia;

public class DenunciaDTORequestBuilder {

    public static CriarDenunciaDtoRequest denunciaDtoRequestValido(){
        return new CriarDenunciaDtoRequest(
                "Endereço válido",
                "Coordenadas",
                TipoDenuncia.DESMATAMENTO_URBANO,
                "Descrição válida"
        );
    }
    public static CriarDenunciaDtoRequest denunciaDtoRequestInvalido() {
        return new CriarDenunciaDtoRequest("", "",
                TipoDenuncia.QUEIMADA_DE_LIXO_DOMESTICO, "");
    }
}
