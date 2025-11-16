package br.com.fiap.AuraPlus.exceptions;

import org.springframework.http.HttpStatus;

public class EquipeNotFoundException extends ApiException {

    private static final String MESSAGE = "Equipe com id %s não encontrada.";

    public EquipeNotFoundException(Long equipeId) {
        super(String.format(MESSAGE, equipeId), HttpStatus.NOT_FOUND);
    }
}
