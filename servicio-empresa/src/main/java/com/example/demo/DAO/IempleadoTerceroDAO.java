package com.example.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entitys.Empleado_tercero;
import com.example.demo.models.Empleado_tercero_pk;

@Repository
public interface IempleadoTerceroDAO extends JpaRepository<Empleado_tercero, Empleado_tercero_pk> {
	@Query("SELECT f FROM Empleado_tercero f WHERE f.empleadoTeceroPk.empleado.numeroDocumento = ?1 AND f.empleadoTeceroPk.tercero.terceroId = ?2")
	public Empleado_tercero buscarEmpleadoTercero(Long pNumeroDocumento,Long pTerceroId);
	@Query("SELECT f FROM Empleado_tercero f WHERE f.empleadoTeceroPk.tercero.tipoTercero.abreviacion = ?1")
	public Empleado_tercero validacionEmpleadoTercero(String pAbreviacion);

}
