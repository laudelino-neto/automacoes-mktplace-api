package br.com.senai.automacoesmktplaceapi.service;

import org.springframework.validation.annotation.Validated;

import br.com.senai.automacoesmktplaceapi.entity.Notificacao;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Validated
public interface EmailService {
	
	default public Notificacao enviar(
			@Valid
			@NotNull(message = "A notificação é obrigatória")
			Notificacao notificacao) {
		throw new UnsupportedOperationException("Operação não suportada");
	}
	
}
