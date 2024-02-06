package com.macedo.apirespiraaripoka.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Localizacao {

    String endereco;
    String coordenadasGeograficas;

    public Localizacao() {
    }

    public Localizacao(String endereco, String coordenadasGeograficas) {
        this.endereco = endereco;
        this.coordenadasGeograficas = coordenadasGeograficas;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public String getCoordenadasGeograficas() {
        return this.coordenadasGeograficas;
    }

}
