package br.com.fiap.AuraPlus;

import br.com.fiap.AuraPlus.dto.request.CadastroSentimentoDto;
import br.com.fiap.AuraPlus.exceptions.SentimentoJaCadastradoException;
import br.com.fiap.AuraPlus.exceptions.UserWithoutTeamException;
import br.com.fiap.AuraPlus.exceptions.UsuarioNotFoundException;
import br.com.fiap.AuraPlus.model.Equipe;
import br.com.fiap.AuraPlus.model.Sentimento;
import br.com.fiap.AuraPlus.model.Usuario;
import br.com.fiap.AuraPlus.model.enums.TipoSentimento;
import br.com.fiap.AuraPlus.repositories.SentimentoRepository;
import br.com.fiap.AuraPlus.repositories.UsuarioRepository;
import br.com.fiap.AuraPlus.service.SentimentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("SentimentoService")
class SentimentoServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private SentimentoRepository sentimentoRepository;

    @InjectMocks
    private SentimentoService sentimentoService;

    private Usuario usuario;
    private CadastroSentimentoDto dto;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste@exemplo.com");

        // Usuário **precisa ter equipe**, pois o método validar exige
        usuario.setEquipe(new Equipe());

        dto = new CadastroSentimentoDto(
                TipoSentimento.FELIZ,
                "Dia muito bom"
        );
    }

    @Test
    @DisplayName("Deve lançar exceção se já existir sentimento cadastrado hoje")
    void cadastrarSentimento_quandoJaExiste_deveLancarExcecao() {
        when(usuarioRepository.findById(usuario.getId()))
                .thenReturn(Optional.of(usuario));

        when(sentimentoRepository.findSentimentoDeHoje(
                eq(usuario.getId()),
                any(LocalDateTime.class),
                any(LocalDateTime.class)
        )).thenReturn(Optional.of(new Sentimento()));

        assertThrows(
                SentimentoJaCadastradoException.class,
                () -> sentimentoService.cadastrarSentimentoDiario(usuario, dto)
        );

        verify(sentimentoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não existe")
    void cadastrarSentimento_quandoUsuarioNaoExiste_deveLancarExcecao() {
        when(usuarioRepository.findById(usuario.getId()))
                .thenReturn(Optional.empty());

        assertThrows(
                UsuarioNotFoundException.class,
                () -> sentimentoService.cadastrarSentimentoDiario(usuario, dto)
        );

        verify(sentimentoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não tem equipe")
    void cadastrarSentimento_quandoUsuarioSemEquipe_deveLancarExcecao() {
        Usuario usuarioSemEquipe = new Usuario();
        usuarioSemEquipe.setId(1L);
        usuarioSemEquipe.setEmail("teste@exemplo.com");
        usuarioSemEquipe.setEquipe(null); // <-- importante

        when(usuarioRepository.findById(usuarioSemEquipe.getId()))
                .thenReturn(Optional.of(usuarioSemEquipe));

        assertThrows(
                UserWithoutTeamException.class,
                () -> sentimentoService.cadastrarSentimentoDiario(usuarioSemEquipe, dto)
        );

        verify(sentimentoRepository, never()).save(any());
    }
}

