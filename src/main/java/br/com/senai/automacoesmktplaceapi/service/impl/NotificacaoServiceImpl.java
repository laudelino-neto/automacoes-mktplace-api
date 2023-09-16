package br.com.senai.automacoesmktplaceapi.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.senai.automacoesmktplaceapi.entity.Notificacao;
import br.com.senai.automacoesmktplaceapi.exception.RegistroNaoEncontradoException;
import br.com.senai.automacoesmktplaceapi.repository.NotificacoesRepository;
import br.com.senai.automacoesmktplaceapi.service.NotificacaoService;

@Service
public class NotificacaoServiceImpl implements NotificacaoService{

	@Autowired
	private NotificacoesRepository repository;
	
	@Override
	public Notificacao inserir(Notificacao notificacao) {
		notificacao.setDataDeEnvio(LocalDateTime.now());
		return repository.save(notificacao);
	}

	@Override
	public Page<Notificacao> listarPor(LocalDate dataDeInicio,
			LocalDate dataDeTermino, Pageable paginacao) {
		Preconditions.checkArgument(dataDeInicio.isBefore(dataDeTermino)
				|| dataDeInicio.isEqual(dataDeTermino), 
				"A data de início não pode ser posterior a data de termino");
		LocalDateTime inicioComHora = LocalDateTime.of(dataDeInicio, LocalTime.of(0, 0));
		LocalDateTime terminoComHora = LocalDateTime.of(dataDeTermino, LocalTime.of(23, 59));
		return repository.listarNotificacoesPor(inicioComHora, terminoComHora, paginacao);
	}

	@Override
	public Notificacao buscarPor(Integer id) {		
		return Optional.ofNullable(repository.buscarPor(id)).orElseThrow(
				() -> new RegistroNaoEncontradoException("Não foi encontrada notificação com o id informado"));		
	}

}
