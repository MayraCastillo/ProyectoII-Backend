package com.example.demo.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.entitys.Empleado_banco;
import com.example.demo.models.Empleado_banco_pk;

@Repository
public interface Iempleado_banco extends JpaRepository<Empleado_banco, Empleado_banco_pk> {
	
	@Query("SELECT e FROM Empleado_banco e WHERE e.empleado_banco_pk.empleado.numeroDocumento = ?1")
	public List<Empleado_banco> listEmpBanco(Long pDocumentoId);
	@Query("SELECT e FROM Empleado_banco e WHERE e.empleado_banco_pk.banco.bancoId = ?1 AND e.empleado_banco_pk.empleado.numeroDocumento = ?2")
	public Empleado_banco validarRelacionEmpleadoBanco(Long pBancoId,Long pNumDocumentoEmp);
}
