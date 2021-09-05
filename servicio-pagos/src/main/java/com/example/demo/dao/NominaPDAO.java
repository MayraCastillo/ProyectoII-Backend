package com.example.demo.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.NominaP;

public interface NominaPDAO extends JpaRepository<NominaP, Long> {
	@Query("SELECT n FROM NominaP n INNER JOIN n.listaDetalleNomina d WHERE d.detalleNominaPk.contratoId = (?1) AND n.fechaFin = (?2)")
	public NominaP validarPeriodoNomina(Long pContratoId, Date pFechaFin);
	
	@Query("SELECT n FROM NominaP n WHERE n.fechaInicio = (?1) AND n.fechaFin = (?2)")
	public NominaP validaNomina(Date pFechaInicio, Date pFechaFin);

}
