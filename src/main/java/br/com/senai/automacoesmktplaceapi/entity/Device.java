package br.com.senai.automacoesmktplaceapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "devices")
@Entity(name = "Device")
public class Device {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@EqualsAndHashCode.Include
	private Integer id;
	
	@Size(max = 100, message = "A descrição não pode possuir mais de 100 caracteres")
	@NotBlank(message = "O título é obrigagatório")
	@Column(name = "descricao")
	private String descricao;
	
	@NotNull(message = "A quantidade de acionadores é obrigatória")
	@Positive(message = "A quantidade de acionadores deve ser positiva")
	@Column(name = "qtde_acionadores")
	private Integer qtdeDeAcionadores;
	
}
