package br.com.fiap.AuraPlus.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record CadastroUserDto(String nome,
                              @Email String email,
                              @Size(min = 6) String senha) {
}
