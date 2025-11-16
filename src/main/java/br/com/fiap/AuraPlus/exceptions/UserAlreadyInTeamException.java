package br.com.fiap.AuraPlus.exceptions;

import org.springframework.http.HttpStatus;

public class UserAlreadyInTeamException extends ApiException {

    private static final String DEFAULT_MESSAGE = "Usuario com email %s ja pertence a uma equipe.";

    public UserAlreadyInTeamException(String email) {
        super(String.format(DEFAULT_MESSAGE, email), HttpStatus.BAD_REQUEST);
    }
}
