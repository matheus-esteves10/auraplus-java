package br.com.fiap.AuraPlus.exceptions;

import org.springframework.http.HttpStatus;

public class UsuarioNotFoundException extends ApiException {

    private static final String MESSAGE = "Usuário não encontrado.";

    public UsuarioNotFoundException() {
        super(MESSAGE, HttpStatus.NOT_FOUND);
    }
}
