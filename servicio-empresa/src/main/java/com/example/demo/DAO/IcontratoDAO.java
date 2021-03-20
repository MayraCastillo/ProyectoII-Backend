package com.example.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.example.demo.entitys.Contrato;

import models.ContratoPk;

@Repository
public interface IcontratoDAO extends JpaRepository<Contrato,ContratoPk> {
	@Query("SELECT c FROM Contrato c WHERE c.contratoPk.empleado.numeroDocumento = ?1 AND c.contratoPk.empresa.nit = ?2")
	public Contrato VerificarContrato(Long pDocumentoEmpleado, Long pNit);
}
