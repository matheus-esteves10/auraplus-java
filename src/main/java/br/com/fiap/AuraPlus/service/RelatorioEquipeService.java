package br.com.fiap.AuraPlus.service;

import br.com.fiap.AuraPlus.dto.broker.producer.RelatorioEquipeDto;
import br.com.fiap.AuraPlus.dto.response.RelatorioLeituraDto;
import br.com.fiap.AuraPlus.exceptions.RelatorioEquipeNotFoundException;
import br.com.fiap.AuraPlus.model.Equipe;
import br.com.fiap.AuraPlus.model.RelatorioEquipe;
import br.com.fiap.AuraPlus.repositories.EquipeRepository;
import br.com.fiap.AuraPlus.repositories.RelatorioEquipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Optional;

@Service
public class RelatorioEquipeService {

    private final EquipeRepository equipeRepository;
    private final RelatorioEquipeRepository relatorioEquipeRepository;

    public RelatorioEquipeService(EquipeRepository equipeRepository, RelatorioEquipeRepository relatorioEquipeRepository) {
        this.equipeRepository = equipeRepository;
        this.relatorioEquipeRepository = relatorioEquipeRepository;
    }

    @Transactional
    public void saveRelatorioEquipe(final String retornoIa, final RelatorioEquipeDto dto) {
        final Equipe equipe = equipeRepository.findById(dto.idEquipe()).orElseThrow();

        RelatorioEquipe relatorioEquipe = new RelatorioEquipe();
        relatorioEquipe.setData(LocalDateTime.now());
        relatorioEquipe.setEquipe(equipe);
        relatorioEquipe.setSentimentoMedio(dto.modaSentimento().name());
        relatorioEquipe.setDescritivo(retornoIa);

        relatorioEquipeRepository.saveAndFlush(relatorioEquipe);
    }

    @Transactional(readOnly = true)
    public RelatorioLeituraDto getRelatorioByEquipeAndMes(Long equipeId, int mes, int ano) {

        RelatorioEquipe relatorio = relatorioEquipeRepository.findByEquipeAndMes(equipeId, mes, ano)
                .orElseThrow(() -> new RelatorioEquipeNotFoundException(mes, ano));

        return new RelatorioLeituraDto(
                relatorio.getData().getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR")).toUpperCase(),
                Year.of(relatorio.getData().getYear()),
                relatorio.getEquipe().getNomeTime(),
                relatorio.getSentimentoMedio(),
                relatorio.getDescritivo()
        );
    }
}

