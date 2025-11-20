package br.com.fiap.AuraPlus.exceptions;

import org.springframework.http.HttpStatus;

public class RelatorioUsuarioNotFoundException extends ApiException {

    private static final String MESSAGE = "Relatório de usuário não encontrado para o mês %s do ano %d.";

    public RelatorioUsuarioNotFoundException(int mes, int ano) {
        super((String.format(MESSAGE, mes, ano)), HttpStatus.NOT_FOUND);
    }
}
