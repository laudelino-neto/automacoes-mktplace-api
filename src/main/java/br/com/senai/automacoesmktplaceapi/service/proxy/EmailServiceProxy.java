package br.com.senai.automacoesmktplaceapi.service.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import br.com.senai.automacoesmktplaceapi.entity.Notificacao;
import br.com.senai.automacoesmktplaceapi.service.EmailService;
import br.com.senai.automacoesmktplaceapi.service.NotificacaoService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

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
		
		try {
			MimeMessage email = mailSender.createMimeMessage();
			email.addHeader("Content-type", "text/HTML; charset=UTF-8");
			MimeMessageHelper helper = new MimeMessageHelper(email, "UTF-8");
			helper.setTo(notificacao.getDestinatario());
			helper.setSubject(notificacao.getTitulo());
			helper.setText(notificacao.getMensagem());
			this.mailSender.send(email);
		} catch (MessagingException e) {
			e.printStackTrace();
		}			
		
		return notificacaoSalva;
		
	}

}
