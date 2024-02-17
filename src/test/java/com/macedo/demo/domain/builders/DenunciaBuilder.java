package com.macedo.demo.domain.builders;

import com.macedo.apirespiraaripoka.entity.Denuncia;
import com.macedo.apirespiraaripoka.util.enums.StatusDenuncia;
import com.macedo.apirespiraaripoka.util.enums.TipoDenuncia;

import java.time.LocalDateTime;

public class DenunciaBuilder {

    public Long id;
    public LocalDateTime dataDenuncia;
    public String endereco;
    public String coordenadasGeograficas;
    public TipoDenuncia tipoDenuncia;
    public String descricao;
    public StatusDenuncia status;
    public LocalDateTime ultimaAtualizacao;

    private DenunciaBuilder(){}

    public static DenunciaBuilder umaDenuncia(){
        return new DenunciaBuilder();
    }

    public static DenunciaBuilder umaDenunciaPadrao(){
        DenunciaBuilder builder = new DenunciaBuilder();
        inicializaDadosPadroes(builder);
        return builder;
    }
    public Denuncia build(){
        return new Denuncia(endereco, coordenadasGeograficas, tipoDenuncia, descricao);
    }

    private static void inicializaDadosPadroes(DenunciaBuilder builder) {
        builder.comIdPadrao();
        builder.comDataDenunciaPadrao();
        builder.comEnderecoPadrao();
        builder.comCoordenadasGeograficasPadrao();
        builder.comTipoDenunciaPadrao();
        builder.comDescricaoPadrao();
        builder.comStatusDenunciaRecebidaPadrao();
    }
    public DenunciaBuilder comIdPadrao(){
        this.id = 1L;
        return this;
    }
    public DenunciaBuilder comDataDenunciaPadrao(){
        this.dataDenuncia = LocalDateTime.now();
        return this;
    }
    public DenunciaBuilder comEnderecoPadrao(){
        this.endereco = "Endereco Padrao";
        return this;
    }
    public DenunciaBuilder comCoordenadasGeograficasPadrao(){
        this.coordenadasGeograficas = "Coordenadas validas";
        return this;
    }
    public DenunciaBuilder comTipoDenunciaPadrao(){
        this.tipoDenuncia = TipoDenuncia.DESMATAMENTO_RURAL;
        return this;
    }
    public DenunciaBuilder comDescricaoPadrao(){
        this.descricao = "Descricao Padrao";
        return this;
    }

    public DenunciaBuilder comStatusDenunciaRecebidaPadrao(){
        this.status = StatusDenuncia.RECEBIDA;
        return this;
    }

    public DenunciaBuilder comIdNulo(){
        this.id = null;
        return this;
    }
    public DenunciaBuilder comEnderecoNulo(){
        this.endereco = null;
        return this;
    }
    public DenunciaBuilder comTipoDenunciaNulo(){
        this.tipoDenuncia = null;
        return this;
    }
    public DenunciaBuilder comDescricaoNulo(){
        this.descricao = null;
        return this;
    }

    public DenunciaBuilder comIdInvalido(){
        this.id = 0L;
        return this;
    }
    public DenunciaBuilder comEnderecoVazio(){
        this.endereco = "";
        return this;
    }
    public DenunciaBuilder comDescricaoVazio(){
        this.descricao = "";
        return this;
    }
}
