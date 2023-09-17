package br.com.senai.automacoesmktplaceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.senai.automacoesmktplaceapi.entity.Device;

@Repository
public interface DevicesRepository extends JpaRepository<Device, Integer>{

	@Query(value = 
			"SELECT d "
			+ "FROM Device d "
			+ "WHERE d.id = :id ")
	public Device buscarPor(Integer id);
	
}
