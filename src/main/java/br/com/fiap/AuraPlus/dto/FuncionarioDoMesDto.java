package br.com.fiap.AuraPlus.dto;

public record FuncionarioDoMesDto(
        String nome,
        int numeroIndicacoes,
        String cargo,
        String equipe,
        String mensagemReconhecimento
) {}
