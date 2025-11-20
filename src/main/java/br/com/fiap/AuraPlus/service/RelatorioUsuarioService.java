package br.com.fiap.AuraPlus.service;

import br.com.fiap.AuraPlus.dto.broker.producer.RelatorioPessoaDto;
import br.com.fiap.AuraPlus.dto.response.RelatorioUsuarioLeituraDto;
import br.com.fiap.AuraPlus.exceptions.RelatorioUsuarioNotFoundException;
import br.com.fiap.AuraPlus.exceptions.UsuarioNotFoundException;
import br.com.fiap.AuraPlus.model.RelatorioPessoa;
import br.com.fiap.AuraPlus.model.Usuario;
import br.com.fiap.AuraPlus.repositories.RelatorioPessoaRepository;
import br.com.fiap.AuraPlus.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.Locale;

@Service
public class RelatorioUsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final RelatorioPessoaRepository relatorioPessoaRepository;

    public RelatorioUsuarioService(UsuarioRepository usuarioRepository, RelatorioPessoaRepository relatorioPessoaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.relatorioPessoaRepository = relatorioPessoaRepository;
    }

    public void saveRelatorioUsuario(final RelatorioPessoaDto dto, final String retornoIa) {
        final Usuario usuario = usuarioRepository.findById(dto.usuarioId()).orElseThrow(UsuarioNotFoundException::new);

        RelatorioPessoa relatorioPessoa = new RelatorioPessoa();
        relatorioPessoa.setData(LocalDateTime.now());
        relatorioPessoa.setUsuario(usuario);
        relatorioPessoa.setNumeroIndicacoes(dto.numeroIndicacoes());
        relatorioPessoa.setDescritivo(retornoIa);

        relatorioPessoaRepository.save(relatorioPessoa);
    }

    @Transactional(readOnly = true)
    public RelatorioUsuarioLeituraDto getRelatorioByUsuarioId(final Long usuarioId, final Integer mes, final Integer ano) {
        final RelatorioPessoa relatorio = relatorioPessoaRepository.findByUserAndMes(usuarioId, mes, ano)
                .orElseThrow(() -> new RelatorioUsuarioNotFoundException(mes, ano));

        return new RelatorioUsuarioLeituraDto(
                relatorio.getNumeroIndicacoes(),
                relatorio.getDescritivo(),
                relatorio.getUsuario().getNome(),
                relatorio.getData().getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR")).toUpperCase(),
                Year.of(relatorio.getData().getYear())
        );
    }
}
