package br.com.fiap.AuraPlus.service;

import br.com.fiap.AuraPlus.dto.request.CadastroSentimentoDto;
import br.com.fiap.AuraPlus.dto.response.SentimentoResponseDto;
import br.com.fiap.AuraPlus.exceptions.SentimentoJaCadastradoException;
import br.com.fiap.AuraPlus.exceptions.UsuarioNotFoundException;
import br.com.fiap.AuraPlus.model.Sentimento;
import br.com.fiap.AuraPlus.model.Usuario;
import br.com.fiap.AuraPlus.repositories.SentimentoRepository;
import br.com.fiap.AuraPlus.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class SentimentoService {

    private final SentimentoRepository sentimentoRepository;
    private final UsuarioRepository usuarioRepository;

    public SentimentoService(SentimentoRepository sentimentoRepository, UsuarioRepository usuarioRepository) {
        this.sentimentoRepository = sentimentoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public SentimentoResponseDto cadastrarSentimentoDiario(final Usuario usuarioLogado, final CadastroSentimentoDto sentimentoDto) {
        final Usuario usuario = findUsuarioById(usuarioLogado.getId());

        if (sentimentoRepository.existsSentimentoDeHoje(usuario.getId())) {
            throw new SentimentoJaCadastradoException(usuario.getEmail());
        }

        Sentimento sentimento = Sentimento.of(
                sentimentoDto.tipo(),
                sentimentoDto.descricao()
        );

        sentimento.setUsuario(usuario);

        sentimentoRepository.save(sentimento);

        return new SentimentoResponseDto(sentimento.getNomeSentimento(), sentimento.getData());
    }

    public SentimentoResponseDto mostrarSentimentoDiario(final Usuario usuarioLogado) {
        final Usuario usuario = findUsuarioById(usuarioLogado.getId());

        final Sentimento sentimentoHoje = sentimentoRepository.findSentimentoDeHoje(usuario.getId()).orElse(null);

        return new SentimentoResponseDto(
                sentimentoHoje != null ? sentimentoHoje.getNomeSentimento() : null,
                sentimentoHoje != null ? sentimentoHoje.getData() : null
        );
    }



    private Usuario findUsuarioById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(UsuarioNotFoundException::new);
    }
}
