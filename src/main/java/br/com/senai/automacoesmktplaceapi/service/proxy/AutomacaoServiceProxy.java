package br.com.senai.automacoesmktplaceapi.service.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.senai.automacoesmktplaceapi.entity.AcaoDeAutomacao;
import br.com.senai.automacoesmktplaceapi.service.AutomacaoService;

@Service
public class AutomacaoServiceProxy implements AutomacaoService {
	
	@Autowired
	@Qualifier("automacaoServiceImpl")
	private AutomacaoService service;

	@Override
	public AcaoDeAutomacao acionarPor(Integer idDoDevice, Integer acionador) {
		return service.acionarPor(idDoDevice, acionador);
	}

	@Override
	public AcaoDeAutomacao desacionarPor(Integer idDoDevice, Integer acionador) {
		return service.desacionarPor(idDoDevice, acionador);
	}

	@Override
	public AcaoDeAutomacao confirmarProcessamentoPor(Integer idDaAcao) {
		return service.confirmarProcessamentoPor(idDaAcao);
	}

	@Override
	public AcaoDeAutomacao buscarProximaAcaoPor(Integer idDoDevice) {
		return service.buscarProximaAcaoPor(idDoDevice);
	}
	
	@Override
	public AcaoDeAutomacao buscarPor(Integer id) {
		return service.buscarPor(id);
	}

}
