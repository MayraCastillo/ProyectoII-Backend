package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.SeguridadSocial;

public interface SeguridadSocialDAO extends JpaRepository<SeguridadSocial, Long> {
	
	@Query("SELECT s FROM SeguridadSocial s WHERE s.nomina.contratoId = ?1")
	public SeguridadSocial obtenerSeguridadSocialEmpleado(Long pContratoId);

}
