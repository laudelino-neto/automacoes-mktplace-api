package br.com.senai.automacoesmktplaceapi.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.senai.automacoesmktplaceapi.entity.AcaoDeAutomacao;
import br.com.senai.automacoesmktplaceapi.entity.Device;
import br.com.senai.automacoesmktplaceapi.entity.enums.Confirmacao;
import br.com.senai.automacoesmktplaceapi.entity.enums.Status;
import br.com.senai.automacoesmktplaceapi.exception.RegistroNaoEncontradoException;
import br.com.senai.automacoesmktplaceapi.repository.AcoesDeAutomacaoRepository;
import br.com.senai.automacoesmktplaceapi.repository.DevicesRepository;
import br.com.senai.automacoesmktplaceapi.service.AutomacaoService;

@Service
public class AutomacaoServiceImpl implements AutomacaoService {	
	
	@Autowired
	private AcoesDeAutomacaoRepository repository;
	
	@Autowired
	private DevicesRepository devicesRepository;

	@Override
	public AcaoDeAutomacao acionarPor(Integer idDoDevice, Integer acionador) {		
		Device deviceEncontrado = buscarDevicePor(idDoDevice);
		boolean isPrimeiraAcao = repository.contarAcoesPor(idDoDevice, acionador) == 0L;
		if (!isPrimeiraAcao) {		
			AcaoDeAutomacao ultimaAcao = getUltimaAcaoPor(deviceEncontrado, acionador);		
			Preconditions.checkArgument(ultimaAcao.isDesacionamento(), "O acionador já está acionado");				
		}else {
			this.validarAcionadorPor(deviceEncontrado, acionador);
		}
		AcaoDeAutomacao novaAcao = new AcaoDeAutomacao(acionador, Status.ACIONADO, deviceEncontrado);		
		return repository.save(novaAcao);
	}
	
	@Override
	public AcaoDeAutomacao desacionarPor(Integer idDoDevice, Integer acionador) {

		Device deviceEncontrado = buscarDevicePor(idDoDevice);

		this.validarAcionadorPor(deviceEncontrado, acionador);

		AcaoDeAutomacao ultimaAcao = repository.buscarUltimaAcaoPor(idDoDevice, acionador);

		Preconditions.checkArgument(ultimaAcao != null && ultimaAcao.isAcionamento(), 
				"O acionador já está desacionado");

		AcaoDeAutomacao novaAcao = new AcaoDeAutomacao(acionador, Status.DESACIONADO, deviceEncontrado);

		return repository.save(novaAcao);

	}
	
	private void validarAcionadorPor(Device device, Integer acionador) {
		Preconditions.checkArgument(device.getQtdeDeAcionadores() >= acionador,
				"Acionador inválido. A faixa de acionadores desse device vai de 1 até " 
						+ device.getQtdeDeAcionadores());
	}
	
	private AcaoDeAutomacao getUltimaAcaoPor(Device device, Integer acionador) {
					
		AcaoDeAutomacao ultimaAcao = repository.buscarUltimaAcaoPor(
				device.getId(), acionador, Confirmacao.N);
		
		Preconditions.checkNotNull(ultimaAcao, "Não existe ação registrada para o acionador '" + acionador + "'");
		
		return ultimaAcao;
		
	}
	
	private Device buscarDevicePor(Integer id) {		
		return Optional.ofNullable(devicesRepository.buscarPor(id)).orElseThrow(
				() -> new RegistroNaoEncontradoException("Não existe device vinculado "
						+ "ao código '" + id + "' "));
	}
	
	@Override
	public AcaoDeAutomacao buscarPor(Integer id) {
		return Optional.ofNullable(repository.buscarPor(id)).orElseThrow(
				() -> new RegistroNaoEncontradoException("Não existe ação vinculada ao id '" + id + "'"));
	}

	@Override
	public AcaoDeAutomacao confirmarProcessamentoPor(Integer idDaAcao) {
		AcaoDeAutomacao acaoEncontrada = buscarPor(idDaAcao);
		Preconditions.checkArgument(!acaoEncontrada.isProcessada(), "Essa ação já foi processada");
		this.repository.atualizarProcessadoPor(acaoEncontrada.getId(), Confirmacao.S);
		return buscarPor(idDaAcao);
	}

	@Override
	public AcaoDeAutomacao buscarProximaAcaoPor(Integer idDoDevice) {
		Device deviceEncontrado = buscarDevicePor(idDoDevice);
		return Optional.ofNullable(repository.buscarProximaAcaoPor(deviceEncontrado.getId()))
				.orElseThrow(() -> new RegistroNaoEncontradoException("Não existem acionadores para processar"));
	}

}
