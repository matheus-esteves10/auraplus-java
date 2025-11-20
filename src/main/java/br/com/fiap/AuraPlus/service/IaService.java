package br.com.fiap.AuraPlus.service;

import br.com.fiap.AuraPlus.dto.broker.producer.RelatorioEquipeDto;
import br.com.fiap.AuraPlus.dto.broker.producer.RelatorioPessoaDto;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

@Service
public class IaService {

    private final ChatModel chatModel;

    public IaService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String processarRelatorioPessoa(final RelatorioPessoaDto dto) {

        var prompt = """
                Você é um assistente especializado em análise de clima organizacional.
                Gere um relatório motivacional, humano e profissional sobre um colaborador,
                baseado nos dados abaixo.
                
                Dados fornecidos:
                - Número de reconhecimentos recebidos: %d
                - Títulos das indicações: %s
                - Descrições das indicações: %s
                
                Instruções:
                1. Estruture o relatório em pequenos parágrafos.
                2. Destaque conquistas e comportamentos positivos citados pelos colegas.
                3. Extraia padrões das descrições (ex.: colaboração, comunicação, liderança).
                4. Mantenha um tom humano, acolhedor e motivacional.
                5. Seja objetivo, evitando exageros e frases genéricas.
                6. Produza um texto final pronto para ser enviado ao colaborador.
                7. Não mencione números específicos ou detalhes que possam identificar os colegas que fizeram as indicações.
                """.formatted(
                dto.numeroIndicacoes(),
                dto.titulos(),
                dto.descritivo());

        return chatModel.call(prompt);
    }

    public String processarRelatorioEquipe(RelatorioEquipeDto dto) {

        var prompt = """
                Você é um analista de clima e performance de equipes.
                Com base nos dados a seguir, gere um relatório aprofundado, claro e profissional
                sobre o estado atual da equipe.
                
                Dados da equipe:
                - Nome da equipe: %s
                - Nota média dos sentimentos (1-5): %.2f
                - Sentimento mais frequente (moda): %s
                - Total de registros analisados: %d
                - Sentimentos reportados: %s
                - Descrições completas dos registros: %s
                
                Instruções:
                1. Comece descrevendo brevemente o humor geral da equipe.
                2. Identifique padrões positivos e pontos fortes que aparecem nos registros.
                3. Aponte pontos de atenção com base em sentimentos negativos, caso existam.
                4. Apresente recomendações práticas e acionáveis para melhorar o ambiente.
                5. Seja direto, evitando clichês e textos genéricos.
                6. Estruture o relatório em seções curtas e bem organizadas.
                7. Tome decisões com base nos dados, evitando suposições sem evidência.
                8. Não cite críticas, nomes específicos ou detalhes sensíveis, coisas que possam identificar indivíduos,
                gerar desconforto ou causar discordia na equipe.
                """.formatted(
                dto.nomeEquipe(),
                dto.mediaSentimentos(),
                dto.modaSentimento(),
                dto.totalReports(),
                dto.sentimentosReportados(),
                dto.descritivo());

        return chatModel.call(prompt);
    }
}
