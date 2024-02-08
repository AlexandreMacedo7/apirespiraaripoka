package com.macedo.apirespiraaripoka.entity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.macedo.apirespiraaripoka.util.enums.StatusDenuncia;
import com.macedo.apirespiraaripoka.util.enums.TipoDenuncia;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "denuncias")
@Entity
public class Denuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private LocalDateTime dateTime;
    private String endereco;
    private String coordenadasGeograficas;
    @Enumerated(EnumType.STRING)
    private TipoDenuncia tipoDenuncia;
    private String descricao;
    @Enumerated(EnumType.STRING)
    private StatusDenuncia status;
    private LocalDateTime ultimaAtualizacao;

    public Denuncia() {
    }

    public Denuncia(String endereco, String coordenadasGeografica, TipoDenuncia tipoDenuncia, String descricao) {
        this.dateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        this.endereco = endereco;
        this.coordenadasGeograficas = coordenadasGeografica;
        this.tipoDenuncia = tipoDenuncia;
        this.descricao = descricao;
        this.status = StatusDenuncia.RECEBIDA;
    }

    public Long getId() {
        return this.id;
    }

    public LocalDateTime getDateTime() {
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

    public StatusDenuncia getStatusDenuncia() {
        return this.status;
    }

    public LocalDateTime getUltimaAtualizacao(){
        return this.ultimaAtualizacao;
    }

    public void atualizaStatusDenuncia(StatusDenuncia status) {
        this.status = status;
        this.ultimaAtualizacao = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }
}
