package br.com.senai.automacoesmktplaceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.senai.automacoesmktplaceapi.entity.AcaoDeAutomacao;
import br.com.senai.automacoesmktplaceapi.entity.enums.Confirmacao;

@Repository
public interface AcoesDeAutomacaoRepository extends JpaRepository<AcaoDeAutomacao, Integer>{
	
	@Modifying
	@Query(value = "UPDATE AcaoDeAutomacao aa SET aa.processado = :processado WHERE aa.id = :idDaAcao")
	public void atualizarProcessadoPor(Integer idDaAcao, Confirmacao processado);
	
	@Query(value = 
			"SELECT aa "
			+ "FROM AcaoDeAutomacao aa "
			+ "JOIN FETCH aa.device d "
			+ "WHERE aa.id = (SELECT Min(aaAux.id) "
			+ "                FROM AcaoDeAutomacao aaAux "
			+ "                WHERE aaAux.device.id = :idDoDevice "
			+ "                AND aaAux.processado = 'N') ")
	public AcaoDeAutomacao buscarProximaAcaoPor(Integer idDoDevice);
	
	@Query(value = 
			"SELECT aa "
			+ "FROM AcaoDeAutomacao aa "
			+ "JOIN FETCH aa.device d "
			+ "WHERE aa.id = (SELECT Max(aaAux.id) "
			+ "                FROM AcaoDeAutomacao aaAux "
			+ "                WHERE aa.device.id = :idDoDevice "
			+ "                AND aa.processado = :processado "
			+ "                AND aa.acionador = :acionador) ")
	public AcaoDeAutomacao buscarUltimaAcaoPor(Integer idDoDevice, 
			Integer acionador, Confirmacao processado);
	
	@Query(value = 
			"SELECT aa "
			+ "FROM AcaoDeAutomacao aa "
			+ "JOIN FETCH aa.device d "
			+ "WHERE aa.id = :id")
	public AcaoDeAutomacao buscarPor(Integer id);
	
	@Query(value = 
			"SELECT Count(aa) "
			+ "FROM AcaoDeAutomacao aa "			
			+ "WHERE aa.device.id = :idDoDevice "
			+ "AND aa.acionador = :acionador "
			+ "AND aa.processado = 'N' ")
	public Long contarAcoesPor(Integer idDoDevice, Integer acionador);

}
