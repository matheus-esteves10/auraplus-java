package br.com.fiap.AuraPlus.exceptions;

import org.springframework.http.HttpStatus;

public class CannotAutoRecognitionException extends ApiException {

    private static final String MESSAGE = "Um usuário não pode reconhecer ele mesmo";

    public CannotAutoRecognitionException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
