package br.com.senai.automacoesmktplaceapi.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.senai.automacoesmktplaceapi.entity.Notificacao;

@Repository
public interface NotificacoesRepository extends JpaRepository<Notificacao, Integer>{

	@Query(value = 
			"SELECT n "
			+ "FROM Notificacao n "
			+ "WHERE n.id = :id")
	public Notificacao buscarPor(Integer id);
	
	@Query(value = 
			"SELECT n "
			+ "FROM Notificacao n "
			+ "WHERE n.dataDeEnvio BETWEEN :dataDeInicio AND :dataDeTermino "
			+ "ORDER BY n.dataDeEnvio DESC ",
			countQuery = 
				"SELECT Count(n) "
				+ "FROM Notificacao n "
				+ "WHERE n.dataDeEnvio BETWEEN :dataDeInicio AND :dataDeTermino ")
	public Page<Notificacao> listarNotificacoesPor(
			LocalDateTime dataDeInicio, LocalDateTime dataDeTermino, Pageable paginacao);
	
}
