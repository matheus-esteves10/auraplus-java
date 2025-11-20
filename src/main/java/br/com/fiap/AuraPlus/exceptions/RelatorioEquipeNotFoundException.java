package br.com.fiap.AuraPlus.exceptions;

import org.springframework.http.HttpStatus;

import java.time.Year;

public class RelatorioEquipeNotFoundException extends ApiException {

    private static final String MESSAGE = "Relatório da equipe não encontrado para o mês %s do ano %d.";

    public RelatorioEquipeNotFoundException(int mes, int ano) {
        super((String.format(MESSAGE, mes, ano)), HttpStatus.NOT_FOUND);
    }
}
