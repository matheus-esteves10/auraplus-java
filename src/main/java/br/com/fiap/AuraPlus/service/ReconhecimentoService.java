package br.com.fiap.AuraPlus.service;

import br.com.fiap.AuraPlus.dto.request.CadastroReconhecimentoDto;
import br.com.fiap.AuraPlus.dto.response.ReconhecimentoResponseDto;
import br.com.fiap.AuraPlus.exceptions.AlreadyRecognizedThisMonthException;
import br.com.fiap.AuraPlus.exceptions.CannotAutoRecognitionException;
import br.com.fiap.AuraPlus.exceptions.UsuarioNotFoundException;
import br.com.fiap.AuraPlus.model.Reconhecimento;
import br.com.fiap.AuraPlus.model.Usuario;
import br.com.fiap.AuraPlus.repositories.ReconhecimentoRepository;
import br.com.fiap.AuraPlus.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ReconhecimentoService {

    private final ReconhecimentoRepository reconhecimentoRepository;
    private final UsuarioRepository usuarioRepository;

    public ReconhecimentoService(ReconhecimentoRepository reconhecimentoRepository, UsuarioRepository usuarioRepository) {
        this.reconhecimentoRepository = reconhecimentoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public ReconhecimentoResponseDto reconhecerColaborador(final Long idReconhecedor,
                                                           final CadastroReconhecimentoDto dto,
                                                           final Long idReconhecido) {

        final Usuario usuarioReconhecedor = findUsuarioById(idReconhecedor);
        final Usuario usuarioReconhecido = findUsuarioById(idReconhecido);

        Reconhecimento.validacoesReconhecimento(usuarioReconhecedor, usuarioReconhecido);

        final boolean jaReconheceuMes = reconhecimentoJaFeitoNoMes(idReconhecedor, idReconhecido);

        if (jaReconheceuMes) {
            throw new AlreadyRecognizedThisMonthException(usuarioReconhecedor.getEmail(), usuarioReconhecido.getEmail());
        }

        Reconhecimento reconhecimento = Reconhecimento.cadastrarReconhecimento(dto.titulo(), dto.descricao());

        reconhecimento.setReconhecedor(usuarioReconhecedor);
        reconhecimento.setReconhecido(usuarioReconhecido);

        reconhecimentoRepository.save(reconhecimento);

        return new ReconhecimentoResponseDto(usuarioReconhecido.getNome(), reconhecimento.getTitulo(),
                reconhecimento.getDescricao(), reconhecimento.getData());
    }

    private Usuario findUsuarioById(final Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(UsuarioNotFoundException::new);
    }

    private boolean reconhecimentoJaFeitoNoMes(final Long idReconhecedor, final Long idReconhecido) {
        final LocalDate primeiroDia = LocalDate.now().withDayOfMonth(1);

        final LocalDateTime inicioMes = primeiroDia.atStartOfDay();
        final LocalDateTime inicioProximoMes = primeiroDia.plusMonths(1).atStartOfDay();

        return reconhecimentoRepository.existsReconhecimentoDoMes(idReconhecedor,
                idReconhecido,
                inicioMes,
                inicioProximoMes
        );
    }


}
