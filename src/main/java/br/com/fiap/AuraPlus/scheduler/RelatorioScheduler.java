package br.com.fiap.AuraPlus.scheduler;

import br.com.fiap.AuraPlus.service.EnvioRelatoriosService;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RelatorioScheduler {

    private final EnvioRelatoriosService envioRelatoriosService;

    public RelatorioScheduler(EnvioRelatoriosService envioRelatoriosService) {
        this.envioRelatoriosService = envioRelatoriosService;
    }

    @PostConstruct
    public void executarAoIniciar() { //método para testar os jobs (roda ao iniciar a aplicação)
        gerarRelatorioEquipes();
        gerarRelatorioUsuarios();
    }


    // Dia 1 - 02:00 da manhã
    @Scheduled(cron = "0 0 2 1 * *")
    public void gerarRelatorioUsuarios() {
        envioRelatoriosService.gerarRelatoriosDoMesUsuarios();
    }

    // Dia 5 - 02:00 da manhã
    @Scheduled(cron = "0 0 2 5 * *")
    public void gerarRelatorioEquipes() {
        envioRelatoriosService.gerarRelatoriosDoMesEquipes();
    }
}
