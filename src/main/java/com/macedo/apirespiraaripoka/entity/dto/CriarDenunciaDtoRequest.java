package com.macedo.apirespiraaripoka.entity.dto;

import com.macedo.apirespiraaripoka.util.enums.TipoDenuncia;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriarDenunciaDtoRequest(
                @NotBlank(message = "Campo não pode ser nulo") String endereco,
                String coordenadasGeograficas,
                @NotNull(message = "Campo não pode ser nulo") TipoDenuncia tipoDenuncia,
                @NotBlank(message = "Campo não pode ser nulo") String descricao) {

}
