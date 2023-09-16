package br.com.senai.automacoesmktplaceapi.service.proxy;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.senai.automacoesmktplaceapi.entity.Notificacao;
import br.com.senai.automacoesmktplaceapi.service.NotificacaoService;

@Service
public class NotificacaoServiceProxy implements NotificacaoService{
	
	@Autowired
	@Qualifier("notificacaoServiceImpl")
	private NotificacaoService service;

	@Override
	public Notificacao inserir(Notificacao notificacao) {
		return service.inserir(notificacao);
	}

	@Override
	public Page<Notificacao> listarPor(LocalDate dataDeInicio, 
			LocalDate dataDeTermino, Pageable paginacao) {
		return service.listarPor(dataDeInicio, dataDeTermino, paginacao);
	}

	@Override
	public Notificacao buscarPor(Integer id) {
		return service.buscarPor(id);
	}

	
	
}
