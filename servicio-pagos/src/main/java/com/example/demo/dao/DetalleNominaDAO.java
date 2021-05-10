package com.example.demo.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.DetalleNomina;
import com.example.demo.model.DetalleNominaPk;

public interface DetalleNominaDAO extends JpaRepository<DetalleNomina, DetalleNominaPk> {
	@Query("SELECT d FROM DetalleNomina d WHERE d.detalleNominaPk.nomina.fechaInicio BETWEEN ?1 AND ?2")
	public List<DetalleNomina> ListarDetallesNomPorPeriodo(Date pFechaInicio, Date pFechaFin);
	
	@Query("SELECT n FROM DetalleNomina n WHERE n.detalleNominaPk.contratoId = ?1 AND n.detalleNominaPk.nomina.nominaId = ?2")
	public DetalleNomina buscarDetalleNomina(Long pContratoId, Long pNominaId);

	@Query("SELECT d FROM DetalleNomina d WHERE d.detalleNominaPk.nomina.fechaInicio BETWEEN ?1 AND ?2 AND d.estado = ?3")
	public List<DetalleNomina> listarDetallesNomPorEstado(Date pFechaInicio, Date pFechaFin, String pEstado);
	
	@Query("SELECT d FROM DetalleNomina d WHERE d.detalleNominaPk.contratoId = ?1 ORDER BY d.detalleNominaPk.nomina.nominaId DESC")
	public List<DetalleNomina> listarDetallesNomPorContrato(Long pContratoId);
	
	@Query("SELECT d FROM DetalleNomina d WHERE d.detalleNominaPk.contratoId = ?1 AND d.estado = ?2 ORDER BY d.detalleNominaPk.nomina.nominaId DESC")
	public List<DetalleNomina> listDetNominaPorContratoYestado(Long pContratoId, String pEstado);
	
	@Query("SELECT d FROM DetalleNomina d WHERE d.detalleNominaPk.nomina.nominaId = ?1 ORDER BY d.detalleNominaPk.nomina.nominaId DESC")
	public List<DetalleNomina> listDetNominaPorIdNomina(Long pNominaId);
	
	@Query("SELECT d FROM DetalleNomina d WHERE d.detalleNominaPk.nomina.nominaId = ?1 AND d.estado = ?2 ORDER BY d.detalleNominaPk.nomina.nominaId DESC")
	public List<DetalleNomina> listDetNominaPorIdNominaYestado(Long pNominaId, String pEstado);
	
	
}
