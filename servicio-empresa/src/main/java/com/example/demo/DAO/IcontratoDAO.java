package com.example.demo.DAO;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.entitys.Contrato;


@Repository
public interface IcontratoDAO extends JpaRepository<Contrato,Long> {
	@Query("SELECT c FROM Contrato c WHERE c.contratoPk.empleado.numeroDocumento = ?1 AND c.contratoPk.empresa.nit = ?2 AND c.estado = 'ACTIVO'")
	public Contrato VerificarContrato(Long pDocumentoEmpleado, Long pNit);
	
	@Query("SELECT c FROM Contrato c WHERE c.contratoPk.empleado.numeroDocumento = ?1 AND c.contratoPk.empresa.nit = ?2 AND c.contratoPk.fechaInicioContrato = ?3")
	public Contrato VerificarPeriodoContrato(Long pDocumentoEmpleado, Long pNit, Date pFechaInicio);
	
	@Query("SELECT c FROM Contrato c WHERE c.contratoPk.empresa.nit = ?1")
	public List<Contrato> listarContratos(Long pNitEmpresa);
	
	public List<Contrato> findByEstado(String pEstado);

}
