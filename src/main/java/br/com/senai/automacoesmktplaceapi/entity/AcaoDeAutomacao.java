package br.com.senai.automacoesmktplaceapi.entity;

import br.com.senai.automacoesmktplaceapi.entity.enums.Confirmacao;
import br.com.senai.automacoesmktplaceapi.entity.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "acoes_automacao")
@Entity(name = "AcaoDeAutomacao")
public class AcaoDeAutomacao {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@EqualsAndHashCode.Include
	private Integer id;
	
	@NotNull(message = "O acionador é obrigatório")
	@Positive(message = "O acionador deve ser positivo")
	@Column(name = "acionador")
	private Integer acionador;
	
	@Enumerated(value = EnumType.STRING)
	@NotNull(message = "O status da ação é obrigatório")
	@Column(name = "status")
	private Status status;
	
	@Enumerated(value = EnumType.STRING)
	@NotNull(message = "O indicador de processamento é obrigatório")
	@Column(name = "processado")
	private Confirmacao processado;
	
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_device")
	@NotNull(message = "O device é obrigatório")
	private Device device;
	
	public AcaoDeAutomacao() {
		this.processado = Confirmacao.N;
	}
	
	public AcaoDeAutomacao(Integer acionador, Status status, Device device) {
		this();
		this.acionador = acionador;
		this.status = status;
		this.device = device;
	}
	
	@Transient
	public boolean isAcionamento() {
		return getStatus() == Status.ACIONADO;
	}
	
	@Transient
	public boolean isDesacionamento() {
		return getStatus() == Status.DESACIONADO;
	}
	
	@Transient
	public boolean isProcessada() {
		return getProcessado() == Confirmacao.S;
	}

}
