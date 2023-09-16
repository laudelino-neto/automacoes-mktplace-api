package br.com.senai.automacoesmktplaceapi.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
	
	@Value("${notificacoes.email.host}")
	private String host;

	@Value("${notificacoes.email.porta}")
	private Integer porta;
	
	@Value("${notificacoes.email.usuario}")
	private String usuario;
	
	@Value("${notificacoes.email.senha}")
	private String senha;
	
	@Value("${notificacoes.email.protocolo}")
	private String protocolo;
	
	@Value("${notificacoes.email.autenticacao}")
	private String autenticacao;
	
	@Value("${notificacoes.email.starttls.enable}")
	private String tlsHabilitado;

	@Bean
	public JavaMailSender getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost(host);
	    mailSender.setPort(porta);
	      
	    mailSender.setUsername(usuario);
	    mailSender.setPassword(senha);
	      
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", protocolo);
	    props.put("mail.smtp.auth", autenticacao);
	    props.put("mail.smtp.starttls.enable", tlsHabilitado);
	      
	    return mailSender;
	}
	
}
