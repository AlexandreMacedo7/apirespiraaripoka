package com.macedo.apirespiraaripoka.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Denuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDateTime dateTime;
    Localizacao Localizacao;
    TipoDenuncia tipoDenuncia;
    String descricao;

    public Denuncia() {
    }

    public Denuncia(LocalDateTime dateTime, Localizacao Localizacao, TipoDenuncia tipoDenuncia, String descricao) {

        this.dateTime = dateTime;
        this.Localizacao = Localizacao;
        this.tipoDenuncia = tipoDenuncia;
        this.descricao = descricao;
    }

    public Long getId() {
        return this.id;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public Localizacao getLocalizacao() {
        return this.Localizacao;
    }

    public TipoDenuncia getTipoDenuncia() {
        return this.tipoDenuncia;
    }

    public String getDescricao() {
        return this.descricao;
    }

}
