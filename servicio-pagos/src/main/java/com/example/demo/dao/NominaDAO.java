package com.example.demo.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Nomina;

@Repository
public interface NominaDAO extends JpaRepository<Nomina, Long> {
	@Query("SELECT n FROM Nomina n WHERE n.contratoId = ?1 AND n.pagoNomina.fechaFin = ?2")
	public Nomina validarNomina(Long pContratoId, Date fechaFin);
}
