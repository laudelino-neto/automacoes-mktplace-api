package br.com.senai.automacoesmktplaceapi.service;

import org.springframework.validation.annotation.Validated;

import br.com.senai.automacoesmktplaceapi.entity.AcaoDeAutomacao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
public interface AutomacaoService {
	
	public AcaoDeAutomacao acionarPor(
			@NotNull(message = "O id do device é obrigatório")
			@Positive(message = "O id do device deve ser positivo")
			Integer idDoDevice, 
			@NotNull(message = "O acionador é obrigatório")
			@Positive(message = "O acionador deve ser positivo")
			Integer acionador);
	
	public AcaoDeAutomacao desacionarPor(
			@NotNull(message = "O id do device é obrigatório")
			@Positive(message = "O id do device deve ser positivo")
			Integer idDoDevice, 
			@NotNull(message = "O acionador é obrigatório")
			@Positive(message = "O acionador deve ser positivo")
			Integer acionador);
	
	public AcaoDeAutomacao confirmarProcessamentoPor(
			@NotNull(message = "O id da ação é obrigatório")
			@Positive(message = "O id da ação deve ser positivo")
			Integer idDaAcao);
	
	public AcaoDeAutomacao buscarProximaAcaoPor(
			@NotNull(message = "O id do device é obrigatório")
			@Positive(message = "O id do device deve ser positivo")
			Integer idDoDevice);
	
	public AcaoDeAutomacao buscarPor(
			@NotNull(message = "O id da ação é obrigatório")
			@Positive(message = "O id da ação deve ser positivo")
			Integer idDaAcao);
	
}
