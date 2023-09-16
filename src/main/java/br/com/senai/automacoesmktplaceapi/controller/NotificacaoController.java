package br.com.senai.automacoesmktplaceapi.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.senai.automacoesmktplaceapi.entity.Notificacao;
import br.com.senai.automacoesmktplaceapi.service.EmailService;
import br.com.senai.automacoesmktplaceapi.service.NotificacaoService;

@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController {
	
	@Autowired
	@Qualifier("notificacaoServiceProxy")
	private NotificacaoService service;
	
	@Autowired
	@Qualifier("emailServiceProxy")
	private EmailService emailService;
	
	@Autowired
	private MapConverter converter;
	
	@PostMapping("/email")
	public ResponseEntity<?> enviarEmailCom(@RequestBody Notificacao notificacao){
		Notificacao notificacaoSalva = emailService.enviar(notificacao);		
		return ResponseEntity.created(URI.create(
				"/notificacoes/id/" + notificacaoSalva.getId())).build();
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> buscarPor(@PathVariable("id") Integer id){		
		return ResponseEntity.ok(converter.toJsonMap(service.buscarPor(id)));
	}
	
	@GetMapping
	public ResponseEntity<?> listarPor(
			@RequestParam(name = "dt-inicio") 
			@DateTimeFormat(iso = ISO.DATE) 
			LocalDate dataDeInicio,
			@RequestParam(name = "dt-termino") 
			@DateTimeFormat(iso = ISO.DATE) 
			LocalDate dataDeTermino,
			@RequestParam("pagina") Optional<Integer> pagina){
		Pageable paginacao = pagina.isPresent() 
				? PageRequest.of(pagina.get(), 15) : PageRequest.of(0, 15);
		return ResponseEntity.ok(converter.toJsonList(service.listarPor(dataDeInicio, dataDeTermino, paginacao)));
	}

}
