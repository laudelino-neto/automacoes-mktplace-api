package br.com.senai.automacoesmktplaceapi.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.senai.automacoesmktplaceapi.entity.AcaoDeAutomacao;
import br.com.senai.automacoesmktplaceapi.service.AutomacaoService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/automacoes")
public class AutomacaoController {

	@Autowired
	private MapConverter converter;
	
	@Autowired
	@Qualifier("automacaoServiceProxy")
	private AutomacaoService service;
	
	@Transactional
	@PostMapping("/device/{idDoDevice}/acionador/{acionador}/acionar")
	public ResponseEntity<?> acionarPor(@PathVariable("idDoDevice") Integer idDoDevice,
			@PathVariable("acionador") Integer acionador){
		AcaoDeAutomacao acaoSalva = service.acionarPor(idDoDevice, acionador);
		return ResponseEntity.created(URI.create(
				"/automacoes/acao/" + acaoSalva.getId())).build();
	}
	
	@Transactional
	@PostMapping("/device/{idDoDevice}/acionador/{acionador}/desacionar")
	public ResponseEntity<?> desacionarPor(@PathVariable("idDoDevice") Integer idDoDevice,
			@PathVariable("acionador") Integer acionador){
		AcaoDeAutomacao acaoSalva = service.desacionarPor(idDoDevice, acionador);
		return ResponseEntity.created(URI.create(
				"/automacoes/acao/" + acaoSalva.getId())).build();
	}
	
	@Transactional
	@PatchMapping("/acao/{idDaAcao}/processar")
	public ResponseEntity<?> processarPor(@PathVariable("idDaAcao") Integer idDaAcao){
		AcaoDeAutomacao acaoAtualizada = service.confirmarProcessamentoPor(idDaAcao);
		return ResponseEntity.ok(converter.toJsonMap(acaoAtualizada));
	}
	
	@GetMapping("/device/{idDoDevice}/proxima")
	public ResponseEntity<?> getProximaAcaoPor(@PathVariable("idDoDevice") Integer idDoDevice){
		AcaoDeAutomacao acaoEncontrada = service.buscarProximaAcaoPor(idDoDevice);
		return ResponseEntity.ok(converter.toJsonMap(acaoEncontrada));
	}
	
	@GetMapping("/acao/id/{idDaAcao}")
	public ResponseEntity<?> buscarPor(@PathVariable("idDaAcao") Integer idDaAcao){
		AcaoDeAutomacao acaoEncontrada = service.buscarPor(idDaAcao);
		return ResponseEntity.ok(converter.toJsonMap(acaoEncontrada));
	}
	
}
