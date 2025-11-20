package br.com.fiap.AuraPlus.service;

import br.com.fiap.AuraPlus.dto.broker.producer.RelatorioPessoaDto;
import br.com.fiap.AuraPlus.exceptions.UsuarioNotFoundException;
import br.com.fiap.AuraPlus.model.RelatorioPessoa;
import br.com.fiap.AuraPlus.model.Usuario;
import br.com.fiap.AuraPlus.repositories.RelatorioPessoaRepository;
import br.com.fiap.AuraPlus.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
}
