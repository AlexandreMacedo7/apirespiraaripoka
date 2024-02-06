package com.macedo.apirespiraaripoka.util.mapper;

import org.springframework.stereotype.Component;

import com.macedo.apirespiraaripoka.entity.Denuncia;
import com.macedo.apirespiraaripoka.entity.dto.CriarDenunciaDtoRequest;
import com.macedo.apirespiraaripoka.entity.dto.DenunciaDtoResponse;

@Component
public class DenunciaMapper {

    public Denuncia toEntity(CriarDenunciaDtoRequest dtoRequest) {

        Denuncia denuncia = new Denuncia(
                dtoRequest.endereco(),
                dtoRequest.coordenadasGeograficas(),
                dtoRequest.tipoDenuncia(),
                dtoRequest.descricao());

        return denuncia;
    }

    public DenunciaDtoResponse toDto(Denuncia denuncia) {
        DenunciaDtoResponse dtoResponse = new DenunciaDtoResponse(denuncia.getId(),
                denuncia.getDateTime(),
                denuncia.getEndereco(),
                denuncia.getCoordenadasGeograficas(),
                denuncia.getTipoDenuncia(),
                denuncia.getDescricao(),
                denuncia.getStatusDenuncia());
        return dtoResponse;
    }
}
