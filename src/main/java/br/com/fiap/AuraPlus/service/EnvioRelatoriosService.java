package br.com.fiap.AuraPlus.service;

import br.com.fiap.AuraPlus.dto.broker.producer.RelatorioEquipeDto;
import br.com.fiap.AuraPlus.dto.broker.producer.RelatorioPessoaDto;
import br.com.fiap.AuraPlus.model.Equipe;
import br.com.fiap.AuraPlus.model.Sentimento;
import br.com.fiap.AuraPlus.model.Usuario;
import br.com.fiap.AuraPlus.model.enums.TipoSentimento;
import br.com.fiap.AuraPlus.producer.RelatorioEquipeProducer;
import br.com.fiap.AuraPlus.producer.RelatorioPessoaProducer;
import br.com.fiap.AuraPlus.repositories.EquipeRepository;
import br.com.fiap.AuraPlus.repositories.ReconhecimentoRepository;
import br.com.fiap.AuraPlus.repositories.SentimentoRepository;
import br.com.fiap.AuraPlus.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EnvioRelatoriosService {

    private final RelatorioPessoaProducer pessoaProducer;
    private  final RelatorioEquipeProducer equipeProducer;
    private final UsuarioRepository usuarioRepository;
    private final ReconhecimentoRepository reconhecimentoRepository;
    private final SentimentoRepository sentimentoRepository;
    private final EquipeRepository equipeRepository;

    public EnvioRelatoriosService(RelatorioPessoaProducer pessoaProducer, RelatorioEquipeProducer equipeProducer, UsuarioRepository usuarioRepository, ReconhecimentoRepository reconhecimentoRepository, SentimentoRepository sentimentoRepository, EquipeRepository equipeRepository) {
        this.pessoaProducer = pessoaProducer;
        this.equipeProducer = equipeProducer;
        this.usuarioRepository = usuarioRepository;
        this.reconhecimentoRepository = reconhecimentoRepository;
        this.sentimentoRepository = sentimentoRepository;
        this.equipeRepository = equipeRepository;
    }

    // ---------------------------------------------------------
    // ------------- RELATÓRIOS DE USUÁRIOS -------------------
    // ---------------------------------------------------------

    public void gerarRelatoriosDoMesUsuarios() {
        final PeriodoMensal periodo = calcularPeriodoMensalTest();

        final List<Long> usuariosIds =
                reconhecimentoRepository.usuariosComIndicacaoNoMes(
                        periodo.inicio(),
                        periodo.fim()
                );

        final List<Usuario> usuarios = usuarioRepository.findAllById(usuariosIds);

        usuarios.forEach(usuario -> processarRelatorioUsuario(usuario, periodo));
    }

    private void processarRelatorioUsuario(final Usuario usuario, final PeriodoMensal periodo) {

        final int indicacoes = reconhecimentoRepository.countByUsuarioNoMes(
                usuario.getId(), periodo.inicio(), periodo.fim()
        );

        final String descritivo = reconhecimentoRepository.descricoesDoMes(
                usuario.getId(), periodo.inicio(), periodo.fim()
        );

        final String titulos = reconhecimentoRepository.titulosDoMes(
                usuario.getId(), periodo.inicio(), periodo.fim()
        );

        final RelatorioPessoaDto dto = RelatorioPessoaDto.builder()
                .usuarioId(usuario.getId())
                .titulos(titulos)
                .numeroIndicacoes(indicacoes)
                .descritivo(descritivo)
                .build();

        System.out.println(dto.toString());
        pessoaProducer.publishMessage(dto);
    }

    // ---------------------------------------------------------
    // ------------- RELATÓRIOS DE EQUIPES ---------------------
    // ---------------------------------------------------------

    public void gerarRelatoriosDoMesEquipes() {
        final PeriodoMensal periodo = calcularPeriodoMensalTest();

        final List<Equipe> equipes = equipeRepository.findAll();

        equipes.forEach(equipe -> processarRelatorioEquipe(equipe, periodo));
    }

    private void processarRelatorioEquipe(Equipe equipe, PeriodoMensal periodo) {

        final List<Sentimento> sentimentosEquipe =
                sentimentoRepository.sentimentosDoMesPorEquipe(
                        equipe.getId(),
                        periodo.inicio(),
                        periodo.fim()
                );


        final double mediaEquipe = sentimentosEquipe.stream()
                .mapToInt(Sentimento::getValorPontuacao)
                .average()
                .orElse(0);

        final TipoSentimento modaSentimento = sentimentosEquipe.stream()
                .collect(Collectors.groupingBy(
                        Sentimento::getNomeSentimento,
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        final String sentimentos = sentimentosEquipe.isEmpty()
                ? "Nenhum sentimento registrado pela equipe no período."
                : sentimentosEquipe.stream()
                .map(s -> s.getNomeSentimento().name())
                .collect(Collectors.joining(" | "));

        final String descricoes = sentimentosEquipe.isEmpty()
                ? "Nenhum sentimento registrado pela equipe no período."
                : sentimentosEquipe.stream()
                .map(Sentimento::getDescricao)
                .collect(Collectors.joining(" || "));

        final RelatorioEquipeDto dto = RelatorioEquipeDto.builder()
                .idEquipe(equipe.getId())
                .mediaSentimentos(mediaEquipe)
                .modaSentimento(modaSentimento)
                .totalReports(sentimentosEquipe.size())
                .sentimentosReportados(sentimentos)
                .descritivo(descricoes)
                .build();

        System.out.println(dto.toString());

        equipeProducer.publishMessage(dto);
    }


    private PeriodoMensal calcularPeriodoMensal() {
        final LocalDate primeiroDiaMesAtual = LocalDate.now().withDayOfMonth(1);
        final LocalDate primeiroDiaMesAnterior = primeiroDiaMesAtual.minusMonths(1);

        return new PeriodoMensal(
                primeiroDiaMesAnterior.atStartOfDay(),
                primeiroDiaMesAtual.atStartOfDay()
        );
    }

    private PeriodoMensal calcularPeriodoMensalTest() { //feito para testar os jobs no mês atual, já que não temos dados do mes anterior
        final LocalDate primeiroDiaMesAtual = LocalDate.now().withDayOfMonth(1);
        final LocalDate ultimoDiaMesAtual = primeiroDiaMesAtual.plusMonths(1).minusDays(1);

        return new PeriodoMensal(
                primeiroDiaMesAtual.atStartOfDay(),
                ultimoDiaMesAtual.atStartOfDay()
        );
    }

    // Record utilitário
    private record PeriodoMensal(LocalDateTime inicio, LocalDateTime fim) { }
}

