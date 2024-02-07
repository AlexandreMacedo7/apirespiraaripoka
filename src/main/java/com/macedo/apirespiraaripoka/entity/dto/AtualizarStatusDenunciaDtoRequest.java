package com.macedo.apirespiraaripoka.entity.dto;

import com.macedo.apirespiraaripoka.util.enums.StatusDenuncia;

import jakarta.validation.constraints.NotBlank;

public record AtualizarStatusDenunciaDtoRequest(@NotBlank StatusDenuncia statusDenuncia) {
}
