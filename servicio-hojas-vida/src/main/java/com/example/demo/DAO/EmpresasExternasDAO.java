package com.example.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.EmpresaExterna;

/**
 * Acceso a la tabla de  empresas externas, que almacena las empresas donde un empleado
 * logró experiencia laboral
 * 
 * @author Ruben
 *
 */

@Repository
public interface EmpresasExternasDAO  extends JpaRepository<EmpresaExterna, Long> {
	
	@Query("SELECT e FROM EmpresaExterna e WHERE e.nombre = ?1 AND e.contacto = ?2 AND e.telefono = ?3")
	public EmpresaExterna buscarEmpresaExterna(String nombre, String contacto, String telefono);
	
}
