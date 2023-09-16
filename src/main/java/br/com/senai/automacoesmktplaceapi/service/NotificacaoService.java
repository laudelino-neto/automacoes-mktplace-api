package br.com.senai.automacoesmktplaceapi.service;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import br.com.senai.automacoesmktplaceapi.entity.Notificacao;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
public interface NotificacaoService {
	
	public Notificacao inserir(
			@Valid
			@NotNull(message = "A notificação é obrigatória")
			Notificacao notificacao);
	
	public Page<Notificacao> listarPor(
			@NotNull(message = "A data de início é obrigatória")
			LocalDate dataDeInicio, 
			@NotNull(message = "A data de término é obrigatória")
			LocalDate dataDeTermino, 
			Pageable paginacao);
	
	public Notificacao buscarPor(
			@NotNull(message = "O id é obrigatório")
			@Positive(message = "O id é positivo")
			Integer id);

}
