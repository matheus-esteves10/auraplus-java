package br.com.fiap.AuraPlus.exceptions;

import org.springframework.http.HttpStatus;

public class NotFromTeamException extends ApiException {

    private static final String MESSAGE = "O usuário %s não pode reconhecer o usuário %s, pois eles não pertencem à mesma equipe.";

    public NotFromTeamException(final String emailReconhecedor, final String emailReconhecido) {
        super(String.format(MESSAGE, emailReconhecedor, emailReconhecido), HttpStatus.BAD_REQUEST);
    }
}
