package br.com.fiap.AuraPlus.exceptions;

import org.springframework.http.HttpStatus;

public class UserWithoutTeamException extends ApiException {

    private static final String MESSAGE = "Usuário %s não pertence a nenhuma equipe.";

    public UserWithoutTeamException(String userMail) {
        super(String.format(MESSAGE, userMail), HttpStatus.BAD_REQUEST);
    }
}
