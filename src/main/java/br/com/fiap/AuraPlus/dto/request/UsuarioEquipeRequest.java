package br.com.fiap.AuraPlus.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UsuarioEquipeRequest(
        @NotBlank String email,
        @NotBlank String cargo
) {}
