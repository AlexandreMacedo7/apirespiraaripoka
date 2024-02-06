package com.macedo.apirespiraaripoka.entity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.macedo.apirespiraaripoka.util.enums.TipoDenuncia;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Denuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private ZonedDateTime dateTime;
    private String endereco;
    private String coordenadasGeograficas;
    private TipoDenuncia tipoDenuncia;
    private String descricao;

    private static ZoneId manauszZoneId = ZoneId.of("America/Manaus");

    public Denuncia() {
    }

    public Denuncia(String endereco, String coordenadasGeografica, TipoDenuncia tipoDenuncia, String descricao) {
        this.dateTime = LocalDateTime.now().atZone(manauszZoneId);
        this.endereco = endereco;
        this.coordenadasGeograficas = coordenadasGeografica;
        this.tipoDenuncia = tipoDenuncia;
        this.descricao = descricao;
    }

    public Long getId() {
        return this.id;
    }

    public ZonedDateTime getDateTime() {
        return this.dateTime;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public String getCoordenadasGeograficas() {
        return this.coordenadasGeograficas;
    }

    public TipoDenuncia getTipoDenuncia() {
        return this.tipoDenuncia;
    }

    public String getDescricao() {
        return this.descricao;
    }
}
