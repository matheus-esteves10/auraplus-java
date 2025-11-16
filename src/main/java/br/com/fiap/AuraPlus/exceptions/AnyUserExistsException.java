package br.com.fiap.AuraPlus.exceptions;

import org.springframework.http.HttpStatus;

public class AnyUserExistsException extends ApiException {

    private static final String MESSAGE = "Nenhum dos emails informados existe no sistema.";

    public AnyUserExistsException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
