package br.com.fiap.AuraPlus.exceptions;

import org.springframework.http.HttpStatus;

public class SentimentoJaCadastradoException extends ApiException {

    private static final String MESSAGE = "O usuário com email %s já cadastrou um sentimento hoje.";

    public SentimentoJaCadastradoException(String email) {
        super(String.format(MESSAGE, email), HttpStatus.BAD_REQUEST);
    }
}
