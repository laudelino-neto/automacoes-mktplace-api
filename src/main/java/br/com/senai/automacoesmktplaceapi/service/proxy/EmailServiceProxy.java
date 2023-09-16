package br.com.senai.automacoesmktplaceapi.service.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import br.com.senai.automacoesmktplaceapi.entity.Notificacao;
import br.com.senai.automacoesmktplaceapi.service.EmailService;
import br.com.senai.automacoesmktplaceapi.service.NotificacaoService;

@Service
public class EmailServiceProxy implements EmailService{

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	@Qualifier("notificacaoServiceProxy")
	private NotificacaoService notificacaoService;
	
	@Value("${notificacoes.email.usuario}")
	private String remetente;

	@Override
	public Notificacao enviar(Notificacao notificacao) {
		
		Notificacao notificacaoSalva = notificacaoService.inserir(notificacao);
		
		SimpleMailMessage email = new SimpleMailMessage();
		email.setFrom(remetente);
		email.setTo(notificacao.getDestinatario());
		email.setSubject(notificacao.getTitulo());
		email.setText(notificacao.getMensagem());
		this.mailSender.send(email);
		
		return notificacaoSalva;
		
	}

}
