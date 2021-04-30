package com.example.demo.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.DetalleNomina;
import com.example.demo.model.DetalleNominaPk;

public interface DetalleNominaDAO extends JpaRepository<DetalleNomina, DetalleNominaPk> {
	@Query("SELECT d FROM DetalleNomina d WHERE d.detalleNominaPk.nomina.fechaInicio = ?1 AND d.detalleNominaPk.nomina.fechaFin = ?2")
	public List<DetalleNomina> ListarDetallesNomPorPeriodo(Date pFechaInicio, Date pFechaFin);
	@Query("SELECT n FROM DetalleNomina n WHERE n.detalleNominaPk.contratoId = ?1 AND n.detalleNominaPk.nomina.nominaId = ?2")
	public DetalleNomina buscarDetalleNomina(Long pContratoId, Long pNominaId);

}
