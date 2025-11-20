package br.com.fiap.AuraPlus.service;

import br.com.fiap.AuraPlus.dto.broker.producer.RelatorioEquipeDto;
import br.com.fiap.AuraPlus.dto.broker.producer.RelatorioPessoaDto;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IaService {

    private final ChatClient chatClient;

    @Autowired
    public IaService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String processarRelatorioPessoa(final RelatorioPessoaDto dto) {

        var prompt = """
                Você é um assistente especializado em clima organizacional.
                 Gere um relatório motivacional, humano e profissional sobre o colaborador, com base nos dados:
                
                - Reconhecimentos recebidos: %d
                - Títulos das indicações: %s
                - Descrições das indicações: %s
                
                Instruções:
                - Organize em pequenos parágrafos.
                - Destaque conquistas e comportamentos positivos identificados nas indicações.
                - Identifique padrões (ex.: colaboração, comunicação, liderança).
                - Use tom acolhedor e motivacional, sem exageros.
                - Gere um texto final direto, adequado para envio ao colaborador.
                - Não mencione números específicos nem detalhes que possam identificar quem fez as indicações.
                
                Regra obrigatória:
                - O resultado deve ser texto contínuo, natural e humano.
                - Nunca retorne números soltos como “0.123” ou qualquer valor isolado.
                - Não retorne listas numéricas.
                """.formatted(
                dto.numeroIndicacoes(),
                dto.titulos(),
                dto.descritivo());

        return chatClient.prompt().user(prompt).call().content();
    }

    public String processarRelatorioEquipe(RelatorioEquipeDto dto) {

        var prompt = """
                Você é um analista de clima e performance. 
                Gere um relatório claro, objetivo e profissional sobre a equipe usando os dados:
                
                - Nome: %s
                - Nota média (1–5): %.2f
                - Sentimento mais frequente: %s
                - Total de registros: %d
                - Sentimentos reportados: %s
                - Descrições completas: %s
                
                Instruções:
                - Resuma o humor geral da equipe.
                - Destaque padrões positivos e pontos fortes.
                - Aponte eventuais sinais de alerta.
                - Dê recomendações práticas para melhorar o clima e colaboração.
                - Seja conciso, sem clichês ou generalizações.
                - Não inclua nomes, dados sensíveis ou críticas diretas.
                
                Regra obrigatória:
                - O resultado deve ser texto contínuo, natural e humano.
                - Não retorne números soltos, códigos, listas ou valores como “0.123”.
                """.formatted(
                dto.nomeEquipe(),
                dto.mediaSentimentos(),
                dto.modaSentimento(),
                dto.totalReports(),
                dto.sentimentosReportados(),
                dto.descritivo());

        return chatClient.prompt().user(prompt).call().content();
    }
}
