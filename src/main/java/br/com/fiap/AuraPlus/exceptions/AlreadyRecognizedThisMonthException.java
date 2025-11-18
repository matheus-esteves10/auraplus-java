package br.com.fiap.AuraPlus.exceptions;

import org.springframework.http.HttpStatus;

public class AlreadyRecognizedThisMonthException extends ApiException {

    private static final String MESSAGE = "O usuário %s já reconheceu o usuário %s este mês";

    public AlreadyRecognizedThisMonthException(String emailReconhecedor, String emailReconhecido) {
        super(String.format(MESSAGE,emailReconhecedor,emailReconhecido), HttpStatus.BAD_REQUEST);
    }
}
