package com.macedo.apirespiraaripoka.util.mapper;

import org.springframework.stereotype.Component;

import com.macedo.apirespiraaripoka.entity.Denuncia;
import com.macedo.apirespiraaripoka.entity.dto.ConsultaStatusDenunciaDtoResponse;
import com.macedo.apirespiraaripoka.entity.dto.CriarDenunciaDtoRequest;
import com.macedo.apirespiraaripoka.entity.dto.DenunciaDetalhadaDtoResponse;

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

    public DenunciaDetalhadaDtoResponse toDto(Denuncia denuncia) {
        DenunciaDetalhadaDtoResponse dtoResponse = new DenunciaDetalhadaDtoResponse(denuncia.getId(),
                denuncia.getDataDenuncia(),
                denuncia.getEndereco(),
                denuncia.getCoordenadasGeograficas(),
                denuncia.getTipoDenuncia(),
                denuncia.getDescricao(),
                denuncia.getStatusDenuncia(),
                denuncia.getUltimaAtualizacao());
        return dtoResponse;
    }

    public ConsultaStatusDenunciaDtoResponse toDtoConsulta(Denuncia denuncia) {
        ConsultaStatusDenunciaDtoResponse dtoResponse = new ConsultaStatusDenunciaDtoResponse(
                denuncia.getId(),
                denuncia.getDataDenuncia(),
                denuncia.getStatusDenuncia(),
                denuncia.getUltimaAtualizacao());
        return dtoResponse;
    }
}
