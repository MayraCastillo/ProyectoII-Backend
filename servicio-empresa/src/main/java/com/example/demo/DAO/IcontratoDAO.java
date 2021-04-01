package com.example.demo.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.entitys.Contrato;


@Repository
public interface IcontratoDAO extends JpaRepository<Contrato,Long> {
	@Query("SELECT c FROM Contrato c WHERE c.contratoPk.empleado.numeroDocumento = ?1 AND c.contratoPk.empresa.nit = ?2 AND c.estado = 'ACTIVO'")
	public Contrato VerificarContrato(Long pDocumentoEmpleado, Long pNit);
	@Query("SELECT c FROM Contrato c WHERE c.contratoPk.empresa.nit = ?1")
	public List<Contrato> listarContratosPorEmpresa(Long pNitEmpresa);
	@Query("SELECT c FROM Contrato c WHERE c.estado = ?1 AND c.contratoPk.empresa.nit = ?2")
	public List<Contrato> listarContratosPorEstado(String pEstado, Long pNitEmpresa);

}
